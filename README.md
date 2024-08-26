
# Ứng Dụng Mô Hình Ngôn Ngữ Lớn Để Khởi Tạo Danh Mục Công Việc và Lệnh SQL Dựa Trên Đặc Tả

## Mục Lục

1. [Mô Tả](#mô-tả)
2. [Cài Đặt Môi Trường - Frontend](cài-đặt-môi-trường-frontend)
    1. [Clone Dự Án Về Máy](#1-clone-dự-án-về-máy)
    2. [Di Chuyển Đến Thư Mục Vừa Clone](#2-di-chuyển-đến-thư-mục-vừa-clone)
    3. [Cài Đặt Các Gói Cần Thiết](#3-cài-đặt-các-gói-cần-thiết)
    4. [Khởi Chạy Dự Án](#4-khởi-chạy-dự-án)
    5. [Truy Cập Giao Diện](#5-truy-cập-giao-diện)
4. [Cài Đặt Môi Trường - Backend](cài-đặt-môi-trường-backend)
    1. [Clone Dự Án Về Máy](#1-clone-dự-án-về-máy)
    2. [Di Chuyển Đến Thư Mục Vừa Clone](#2-di-chuyển-đến-thư-mục-vừa-clone)
    3. [Cài Đặt Môi Trường Cho Dự Án](#3-cài-đặt-môi-trường-cho-dự-án)
    4. [Khởi Chạy Dự Án](#4-khởi-chạy-dự-án)
    5. [Truy Cập Giao Diện](#5-truy-cập-giao-diện)
5. [Cấu hình dự án - Backend](cấu-hình-dự-án)

## Mô Tả

Dự án này sử dụng mô hình ngôn ngữ lớn để tự động khởi tạo danh mục công việc và lệnh SQL dựa trên đặc tả yêu cầu. Với các tính năng mạnh mẽ và giao diện thân thiện, ứng dụng giúp người dùng dễ dàng quản lý và theo dõi công việc cũng như sinh các lệnh SQL một cách hiệu quả.

## Cài Đặt Môi Trường - Frontend

Clone dự án frontend tại:  
[https://github.com/annhducit/TaskSmart_UI.git](https://github.com/annhducit/TaskSmart_UI.git)

### 1. Clone Dự Án Về Máy

```bash
git clone https://github.com/annhducit/TaskSmart_UI.git
```

### 2. Di Chuyển Đến Thư Mục Vừa Clone

Di chuyển terminal đến thư mục dự án vừa clone về:

```bash
cd TaskSmart_UI
```

### 3. Cài Đặt Các Gói Cần Thiết

Sử dụng npm để cài đặt các gói cần thiết cho frontend:

```bash
npm install
```

### 4. Khởi Chạy Dự Án

Khởi chạy dự án frontend:

```bash
npm run dev
```

### 5. Truy Cập Giao Diện

Truy cập http://localhost:3000/ để vào giao diện hệ thống TaskSmart.

## Cài Đặt Môi Trường - Backend

Clone dự án backend tại:  
[https://github.com/hducduy21/TaskSmart](https://github.com/hducduy21/TaskSmart)

### 1. Clone Dự Án Về Máy

```bash
git clone https://github.com/hducduy21/TaskSmart.git
```

### 2. Di Chuyển Đến Thư Mục Vừa Clone

Di chuyển terminal đến thư mục dự án vừa clone về:

```bash
cd TaskSmart
```

### 3. Cài đặt môi trường cho dự án

Tiến hành cài đặt các môi trường cần thiết và khởi chạy các container:

```bash
docker compose up
```
Các container cần thiết để chạy dự án bao gồm kafka và zookeeper
Để chạy monitoring, cần chạy các container có đuôi -service (lưu ý, chạy container config-service đầu tiên)

### 4. Khởi Chạy Dự Án

Khởi chạy từng service lần lượt theo thứ tự như sau:

1. cloud-config
2. eureka-server
3. api-gateway
4. Các service còn lại  
   *Lưu ý:* Service `activity-tracker` nên được khởi chạy sau cùng.

Đối với PyHelper service, di chuyển terminal đến thư mục `PyHelper` và thực hiện các bước sau:

```bash
pip install -r requirements.txt
```

Sau khi cài đặt các thư viện Python, khởi chạy PyHelper:

```bash
python.exe -m uvicorn main:app --reload --port 8807
```

### 5. Truy Cập Giao Diện

Sau khi các service đã được khởi chạy thành công, hệ thống sẽ sẵn sàng hoạt động. Truy cập vào giao diện người dùng thông qua trình duyệt để bắt đầu sử dụng ứng dụng.

## Cấu hình dự án - Backend

Cấu hình cho từng service của dự án backend tại:  
[https://github.com/hducduy21/TaskSmart_Configuration](https://github.com/hducduy21/TaskSmart_Configuration)
