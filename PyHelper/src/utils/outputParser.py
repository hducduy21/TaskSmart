from langchain_core.pydantic_v1 import BaseModel, Field

class CheckList(BaseModel):
    name: str = Field(description="The name of the to-do")

class CheckListGroup(BaseModel):
    name: str = Field(description="The name of the check list, to-do group")
    checkList: list[CheckList] = Field(description="The checklists/to-do in the group")

class Card(BaseModel):
    name: str = Field(description="The title of the card/task")
    description: str = Field(description="The description of the card/task")
    checkLists: list[CheckList] = Field(description="The checklists in the card, a group to do in task, card")

class ListCard(BaseModel):
    name: str = Field(description="The name of the list")
    cards: list[Card] = Field(description="Cards are the most granular element of the system, representing individual tasks or work items. Cards contain detailed information about the task, including titles, descriptions, due dates, and assigned users.")

class Project(BaseModel):
    listCards: list[ListCard] = Field(description="Lists are used to represent stages of a project, categories of tasks, or any other logical grouping that makes sense for the project at hand.")

