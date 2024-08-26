import boto3
from botocore.exceptions import NoCredentialsError, PartialCredentialsError
from fastapi import UploadFile, HTTPException

AWS_S3_ACCESS_KEY = "AKIAY42F3KJNL5JRGJMD"
AWS_S3_SECRET_ACCESS_KEY = "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"
AWS_S3_BUCKET_NAME = "tasksmart-development"
AWS_REGION = "ap-southeast-2"
client = boto3.client(
    "s3",
    aws_access_key_id=AWS_S3_ACCESS_KEY,
    aws_secret_access_key=AWS_S3_SECRET_ACCESS_KEY
)
class AWS:
    @staticmethod
    def read_from_s3(file_name):
        try:
            response = client.get_object(Bucket=AWS_S3_BUCKET_NAME, Key=file_name)
            file_content = response['Body'].read()
            return file_content, True
        except NoCredentialsError:
            return "Credentials not available", False
        except Exception as e:
            return str(e), False

    @staticmethod
    def put_to_s3(file: UploadFile):
        try:
            client.upload_fileobj(
                file.file,
                AWS_S3_BUCKET_NAME,
                file.filename
            )
            return {"filename": file.filename}
        except NoCredentialsError:
            raise HTTPException(status_code=403, detail="AWS credentials not available")
        except PartialCredentialsError:
            raise HTTPException(status_code=403, detail="Incomplete AWS credentials")
        except Exception as e:
            raise HTTPException(status_code=500, detail=f"Upload failed: {str(e)}")