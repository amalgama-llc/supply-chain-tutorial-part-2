# Step 2: Adding Road Network

## About
This repository contains the source code for the [Step 2 in the Amalgama Platform tutorial](https://platform.amalgamasimulation.com/amalgama/platform_tutorial_step_2.html).

The application simulates the functionality of a simple supply chain.
A set of experiments is run to find the optimal number of trucks to move cargo among warehouses and stores.
Truck move within a road network.

## How to build and run

1. Clone the repository to your local machine.
1. [Get access](https://platform.amalgamasimulation.com/amalgama/quick_start_access.html) to the Amalgama Platform libraries.
1. Install and configure Eclipse IDE.
1. Open the project in Eclipse. [Download and add external libraries](https://platform.amalgamasimulation.com/amalgama/quick_start_console.html#_external_libraries) to the project:
    * com.amalgamasimulation.utils
    * com.amalgamasimulation.engine
    * com.amalgamasimulation.graph
    * com.amalgamasimulation.graphagent
    * com.amalgamasimulation.geometry
    * commons-math3
1. Start the console application using the `main()` method in the `Main` class.

This gets printed to the console:

> Trucks count:	11	SL:	2,18%	Expenses:	\$ 285 808,12	Expenses/SL:	$ 131 254,66  
Trucks count:	12	SL:	2,48%	Expenses:	\$ 311 702,15	Expenses/SL:	$ 125 650,60  
Trucks count:	13	SL:	2,87%	Expenses:	\$ 337 571,06	Expenses/SL:	$ 117 760,37  
Trucks count:	14	SL:	3,61%	Expenses:	\$ 363 412,19	Expenses/SL:	$ 100 645,76  
Trucks count:	15	SL:	5,46%	Expenses:	\$ 389 242,92	Expenses/SL:	$ 71 321,88  
Trucks count:	16	SL:	10,06%	Expenses:	\$ 415 071,85	Expenses/SL:	$ 41 257,01  
Trucks count:	17	SL:	56,13%	Expenses:	\$ 440 870,43	Expenses/SL:	$ 7 854,02  
Trucks count:	18	SL:	100,00%	Expenses:	\$ 449 053,67	Expenses/SL:	$ 4 490,54  
Trucks count:	19	SL:	100,00%	Expenses:	\$ 458 407,33	Expenses/SL:	$ 4 584,07  
Trucks count:	20	SL:	100,00%	Expenses:	\$ 464 122,53	Expenses/SL:	$ 4 641,23  
Trucks count:	21	SL:	100,00%	Expenses:	\$ 471 980,74	Expenses/SL:	$ 4 719,81  
Trucks count:	22	SL:	100,00%	Expenses:	\$ 479 990,81	Expenses/SL:	$ 4 799,91  
Trucks count:	23	SL:	100,00%	Expenses:	\$ 487 510,12	Expenses/SL:	$ 4 875,10  
Trucks count:	24	SL:	100,00%	Expenses:	\$ 493 083,41	Expenses/SL:	$ 4 930,83  
Trucks count:	25	SL:	100,00%	Expenses:	\$ 501 924,77	Expenses/SL:	$ 5 019,25  