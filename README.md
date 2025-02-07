# DEVELOPMENT OF A CODELESS MODEL FOR MODEL-BASED AUTOMATIC UNIT TESTING OF COMBINATORIAL AND SEQUENTIAL CODE IN SIEMENS PLCs
**by A. Seniow**

## Thesis Information
This project was developed as part of the thesis:  
**_"DEVELOPMENT OF A CODELESS MODEL FOR MODEL-BASED AUTOMATIC UNIT TESTING OF COMBINATORIAL AND SEQUENTIAL CODE IN SIEMENS PLCs"_**  
in partial fulfillment of the requirements for the degree of  
**Master of Science in Software Engineering**  
at the Open University, Faculty of Management, Science and Technology.

- **Master Program**: Master Software Engineering  
- **Public Defense Date**: DayMonth DD, YYYY at HH:00 PM  
- **Student Number**: 838374528  
- **Course Code**: IM9906  
- **Thesis Committee**:  
  - Dr. S. Schivo (first supervisor),   Open University  
  - Dr. F. Verbeek (second supervisor), Open University  

## Table of Contents
1. [Description](#description)
2. [Acknowledgements](#acknowledgements)

## Description  
This project focuses on developing a **codeless model** for **Model-Based Testing (MBT)** to automate unit testing of **combinatorial and sequential logic** in Siemens PLCs. The goal is to replace manual test creation with an **intuitive graphical model**, enabling automatic test cycle generation and execution.  

The approach eliminates the need for complex **adapter code**, allowing testers to define states, transitions, and expected outputs visually. The model is then used to automatically validate PLC logic by interacting directly with the system under test (SUT).  

This method ensures that the PLC code meets its **functional correctness** requirements by verifying whether the expected output variables are produced for given input variables. It does not cover aspects such as **security testing, failure detection due to incorrect inputs, or code coverage analysis**.  


## Acknowledgements  
This project makes use of the **MOKA 7 library** for interfacing with Siemens PLCs. The library is utilized for handling low-level communication and interactions with the PLCs, and its inclusion has been essential in achieving the project's objectives. The MOKA 7 library was obtained from [SourceForge](https://sourceforge.net/projects/snap7/files/Moka7/1.0.1/moka7-full-1.0.1.zip/download).  

Additionally, the implementation of the graphical user interface (GUI) for the modeling tool is based on various resources related to **Java 2D graphics and interactive visualization techniques**. These resources provided essential functionalities for rendering states as circles, transitions as paths, and labels as text. Furthermore, they facilitated user interaction, including selecting, moving, and organizing elements within the drawing canvas. For detailed information, refer to the sources listed in the **References** section.  

Special thanks to the MOKA 7 developers for providing this valuable tool.  

---

## References  
[1] Oracle, *Java 2D Graphics and Interactive UI Development*, Oracle Documentation. Available: [https://docs.oracle.com/javase/tutorial/](https://docs.oracle.com/javase/tutorial/). Accessed: *[01/05/2024]*.  
Various sections of the Oracle Java documentation were consulted for implementing the graphical user interface of this project. These include **Java 2D Graphics**, **painting techniques**, **rendering optimizations**, **event handling**, **user interaction management**, and **transformations**. The concepts covered were essential in enabling the visual representation of states (circles), transitions (paths), and labels (text), as well as supporting interactive element manipulation within the modeling environment.  

[2] SourceForge, *MOKA 7 Library*, Available: [https://sourceforge.net/projects/snap7/files/Moka7/1.0.1/moka7-full-1.0.1.zip/download](https://sourceforge.net/projects/snap7/files/Moka7/1.0.1/moka7-full-1.0.1.zip/download). Accessed: *[01/05/2024]*.  
