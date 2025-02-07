# DEVELOPMENT OF A CODELESS MODEL FOR MODEL-BASED AUTOMATIC UNIT TESTING OF COMBINATORIAL AND SEQUENTIAL CODE IN SIEMENS PLCs
**by A. Seniow**

## Thesis Information
This project was developed as part of the thesis:  
**_"DEVELOPMENT OF A CODELESS MODEL FOR MODEL-BASED AUTOMATIC UNIT TESTING OF COMBINATORIAL AND SEQUENTIAL CODE IN SIEMENS PLCs"_**  
in partial fulfillment of the requirements for the degree of  
**Master of Science in Software Engineering**  
at the Open University, faculty of Science
Master Software Engineering.

- **Master Program**: Master Software Engineering  
- **Public Defense Date**: Tuesday 22nd April 2025 at 14:00
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

To better understand and communicate the internal structure of the code, **PlantUML class diagrams** were added. These diagrams provide a visual representation of the **software design** and the relationships between key components.

## Acknowledgements

This project makes use of the **MOKA 7 library** [1] for interfacing with Siemens PLCs. The library was directly integrated into the project to facilitate low-level communication and interaction with the PLCs. Its inclusion was essential to achieving the objectives of this project. The MOKA 7 library was obtained from [SourceForge](https://sourceforge.net/projects/snap7/files/Moka7/1.0.1/moka7-full-1.0.1.zip/download) and is used in accordance with its distribution license.

Additionally, the implementation of the graphical user interface (GUI) for the modeling tool is based on a range of resources related to **Java 2D graphics and interactive visualization techniques**. These resources [2][3][4][5] provided essential insights into rendering states as circles, transitions as paths, and labels as text. They also inspired the handling of user interactions such as grid alignment, snapping, selection, movement, and the organization of elements within the drawing canvas.

> ⚠️ **Note**: The MOKA 7 library was fully included in the project as-is and is used in its original form.  
> All other external resources were consulted for **conceptual understanding and inspiration**.  

Furthermore, the implementation of the **depth-first search (DFS)** algorithm used for model analysis is based on the algorithmic principles described in [6]. This reference provided a clear and structured explanation of DFS traversal, which was adapted to suit the needs of the modeling logic in this tool.

Special thanks go to the developers of MOKA 7 for providing this valuable library.

### License Information

The MOKA 7 library is distributed under the **GNU General Public License v3.0 (GPL-3.0)**.  
The full license text is publicly available at: [https://www.gnu.org/licenses/gpl-3.0.en.html](https://www.gnu.org/licenses/gpl-3.0.en.html)

---

## References

[1] SourceForge, *MOKA 7 Library*. Available: [https://sourceforge.net/projects/snap7/files/Moka7/1.0.1/moka7-full-1.0.1.zip/download](https://sourceforge.net/projects/snap7/files/Moka7/1.0.1/moka7-full-1.0.1.zip/download). Accessed: *May 1, 2024*.

[2] Oracle, *Trail: 2D Graphics*, Oracle Documentation. Available: [https://docs.oracle.com/javase/tutorial/2d/index.html](https://docs.oracle.com/javase/tutorial/2d/index.html). Accessed: *May 1, 2024*.  
Various sections of the Oracle Java documentation were consulted in the implementation of the graphical user interface. These include topics on **Java 2D graphics**, **painting techniques**, **rendering optimizations**, **event handling**, **user interaction management**, and **transformations**.

[3] Y. D. Liang and H. Zhang, *Computer Graphics Using Java 2D and 3D*. Upper Saddle River, NJ: Pearson Prentice Hall, 2006.

[4] C. Haase and R. Guy, *Filthy Rich Clients: Developing Animated and Graphical Effects for Desktop Java Applications*. Upper Saddle River, NJ: Addison-Wesley, 2007.

[5] Stack Overflow. (n.d.). *Search results for “Java 2D”*. Stack Overflow. Retrieved May 1, 2024, from [https://stackoverflow.com/questions/tagged/java-2d](https://stackoverflow.com/questions/tagged/java-2d)

[6] M. T. Goodrich, R. Tamassia, and M. H. Goldwasser, *Data Structures and Algorithms in Java* (6th ed.). Hoboken, NJ: John Wiley & Sons, 2015. ISBN: 978-1-118-80857-3.
