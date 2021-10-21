# [Team Name] Report

The following is a report template to help your team successfully provide all the details necessary for your report in a structured and organised manner. Please give a straightforward and concise report that best demonstrates your project. Note that a good report will give a better impression of your project to the reviewers.

*Here are some tips to write a good report:*

* *Try to summarise and list the `bullet points` of your project as much as possible rather than give long, tedious paragraphs that mix up everything together.*

* *Try to create `diagrams` instead of text descriptions, which are more straightforward and explanatory.*

* *Try to make your report `well structured`, which is easier for the reviewers to capture the necessary information.*

*We give instructions enclosed in square brackets [...] and examples for each sections to demonstrate what are expected for your project report.*

*Please remove the instructions or examples in `italic` in your final report.*

## Table of Contents

1. [Team Members and Roles](#team-members-and-roles)
2. [Conflict Resolution Protocol](#conflict-resolution-protocol)
2. [Application Description](#application-description)
3. [Application UML](#application-uml)
3. [Application Design and Decisions](#application-design-and-decisions)
4. [Summary of Known Errors and Bugs](#summary-of-known-errors-and-bugs)
5. [Testing Summary](#testing-summary)
6. [Implemented Features](#implemented-features)
7. [Team Meetings](#team-meetings)

## Team Members and Roles

| UID | Name | Role |
| :--- | :----: | ---: |
| [uid] | [name] | [role] |
| [uid] | [name] | [role] |
| [uid] | [name] | [role] |
| [uid] | [name] | [role] |

## Conflict Resolution Protocol

*[Write a well defined protocol your team can use to handle conflicts. That is, if your group has problems, what is the procedure for reaching consensus or solving a problem? (If you choose to make this an external document, link to it here)]*
### Conflict with a Team member
Any conflict that arises with members of the project team will require the individual involved in
the conflict to contact the Project Manager. The Project Manager will arrange and facilitate a
meeting with the aggrieved parties accompanied by a detailed description of each individual's
comments and concerns. If the conflict in question involves the Project Manager then a Team
Member who is not involved in the conflict will fill the role of Project Manager for the purposes of
resolving this specific conflict. The meeting will begin with each person, in no particular order,
individually explaining their concerns and discussing them openly. Notes will be taken
throughout this process including key comments and desired outcomes. The purpose of this
meeting is for all parties to come to a mutual understanding and reach a resolution to the
aforementioned conflict. If a solution cannot be agreed upon, the conflict will be escalated and
require mediation as described in the Mediation part.

### Conflict with the Convenor
Any conflict that arises with the Convenor will require the individual, or individuals, involved in
the conflict to contact the Project Manager. The Project Manager will arrange and facilitate a
meeting with the aggrieved parties accompanied by a detailed description of each individual's
comments and concerns. If the conflict in question involves the Project Manager then a Team
Member who is not involved in the conflict will fill the role of Project Manager for the purposes of
resolving this specific conflict. The meeting will begin with each person, in no particular order,
individually explaining their concerns and discussing them openly. Notes will be taken
throughout this process including key comments and desired outcomes. The purpose of this
meeting is for all parties to come to a mutual understanding and reach a resolution to the
aforementioned conflict. If a solution cannot be agreed upon, the conflict will be escalated and
require mediation as described in the Mediation part.

### Mediation
In the unlikely event that an event of conflict can not be resolved it will require mediation from a
third party. This can be at the request of the aggrieved parties if they believe they can not
resolve the conflict through the usual means. It is the responsibility of the Project Manager to
organise an appropriate person to undertake mediation. If there is a conflict of interest with the
Project Manager, the next appropriate individual will be the Assessor followed by the Course
Convenor.

## Application Description

*[What is your application, what does it do? Include photos or diagrams if necessary]*

*Here is a pet specific social media application example*

*PetBook is a social media application specifically targetting pet owners... it provides... certified practitioners, such as veterians are indicated by a label next to their profile...*

**Application Use Cases and or Examples**

*[Provide use cases and examples of people using your application. Who are the target users of your application? How do the users use your application?]*

*Here is a pet training application example*

*Molly wants to inquiry about her cat, McPurr's recent troublesome behaviour*
1. *Molly notices that McPurr has been hostile since...*
2. *She makes a post about... with the tag...*
3. *Lachlan, a vet, writes a reply to Molly's post...*
4. ...
5. *Molly gives Lachlan's reply a 'tick' response*

*Here is a map navigation application example*

*Targets Users: Drivers*

* *Users can use it to navigate in order to reach the destinations.*
* *Users can learn the traffic conditions*
* ...

*Target Users: Those who want to find some good restaurants*

* *Users can find nearby restaurants and the application can give recommendations*
* ...

*List all the use cases in text descriptions or create use case diagrams. Please refer to https://www.visual-paradigm.com/guide/uml-unified-modeling-language/what-is-use-case-diagram/ for use case diagram.*

## Application UML

![ClassDiagramExample](./images/ClassDiagramExample.png)
*[Replace the above with a class diagram. You can look at how we have linked an image here as an example of how you can do it too.]*

## Application Design and Decisions

*Please give clear and concise descriptions for each subsections of this part. It would be better to list all the concrete items for each subsection and give no more than `5` concise, crucial reasons of your design. Here is an example for the subsection `Data Structures`:*

*I used the following data structures in my project:*

1. *LinkedList*

   * *Objective: It is used for storing xxxx for xxx feature.*

   * *Locations: line xxx in XXX.java, ..., etc.*

   * *Reasons:*

     * *It is more efficient than Arraylist for insertion with a time complexity O(1)*

     * *We don't need to access the item by index for this feature*

2. ...

3. ...

**Data Structures**

*[What data structures did your team utilise? Where and why?]*

**Design Patterns**

*[What design patterns did your team utilise? Where and why?]*

**Grammars**

*Search Engine*
<br> *Production Rules* <br>
\<Non-Terminal> ::= \<some output>
<br>
\<Non-Terminal> ::= \<some output>

*[How do you design the grammar? What are the advantages of your designs?]*

*If there are several grammars, list them all under this section and what they relate to.*

**Tokenizer and Parsers**

*[Where do you use tokenisers and parsers? How are they built? What are the advantages of the designs?]*

**Surpise Item**

*[If you implement the surprise item, explain how your solution addresses the surprise task. What decisions do your team make in addressing the problem?]*

**Other**

*[What other design decisions have you made which you feel are relevant? Feel free to separate these into their own subheadings.]*

## Summary of Known Errors and Bugs

*[Where are the known errors and bugs? What consequences might they lead to?]*

*Here is an example:*

1. *Bug 1:*
- *User avatar does not show in the activity_main.* 

2. *Bug 2:*
- *the login detail of a user would lost when quit from the app.   
3. ...

*List all the known errors and bugs here. If we find bugs/errors that your team do not know of, it shows that your testing is not through.*

## Testing Summary

*[What features have you tested? What is your testing coverage?]*

*Here is an example:*

*Number of test cases: ...*

*Code coverage: ...*

*Types of tests created: ...*

*Please provide some screenshots of your testing summary, showing the achieved testing coverage. Feel free to provide further details on your tests.*

## Implemented Features

*[What features have you implemented?]*

*Here is an example:*

*Basic App*

1. *Login and Sign up. User can be able to login an sign up. (basic)*
      status: completed
      implemented feature: Login and sign up.
   
2. *One fully implemented data structure taught in this course  (basic)*
      status: completed
      implemented feature: RBtree.
   
   
3. *Search functionality that makes use of tokenizer and parser. (basic)*
      status: completed
      implemented feature: Use of tokenizer and parser

4. *two design pattern. (basic)*
      status: completed
      implemented feature: DAO patter and singleton pattern.
   
5. *data file with at least 1,000 valid data instances.(basic)*
      status: completed
      implemented feature: data file with 1500 data instances.
   
6. *Load and view posts and  retrieve data from a local file.(basic)*
      status: completed
      implemented feature: load and view post.

*Advanced future*

1. *Read data instances from multiple local file in different formats . (Easy)*
      status: completed
      implemented feature: make use of json file and csv file.
   
2. *UI can support for different scree size and have portrait and landscape layout variants . (Easy)*
      status: completed
      implemented feature: UI including Login, register, main interface can switch between portrait and landscape layout in different scree size.
   
3. *User profile activity containing a media file. (Easy)*    
      status: completed
      implemented feature: Different users have different avatars
   
4. *Use Gps Information . (Easy)*    
      status: completed
      implemented feature: GPS information is on the right side of the user's profile picture.
   
5. *The ability to micro-interact with 'posts' . (Easy)*    
      status: completed
      implemented feature: user can like a post.

6. *User statistics. (Medium)*    
      status: completed
      implemented feature: total like, total posts and total followers of users can be seen.
   
7. *Improved search. (Medium)*    
      status: completed
      implemented feature: search function can handle partially valid input and invalid input.






















## Team Meetings

*Here is an example:*

- *[Team Meeting 1](./meeting/meeting1.md)*
- *[Team Meeting 2](./meeting/meeting2.md)*
- *[Team Meeting 3](./meeting/meeting3.md)*
- *[Team Meeting 4](./meeting/meeting4.md)*
- *[Team Meeting 5](./meeting/meeting5.md)*

*Either write your meeting minutes here or link to documents that contain them. There must be at least 3 team meetings.*