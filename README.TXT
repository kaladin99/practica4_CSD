

Las clases importantes son:
Ants.java
Terrain1.java
Terrain2.java
Terrain3.java


Recordad cambiar en la clase ants.java el nombre de los ficheros en el switch tiene que quedar así al final:
no lo cambieis al principio todo si no no compilarà, o cambiadlo y cread todas las clases con la plantilla e Terrain0-.java

switch (readArg(args,0,0,0,3)) {
        case 0: t=new Terrain0(T,A,M,"0.- Basic monitor:"); break;
        case 1: t=new Terrain1(T,A,M,"1.- General monitor, 1 cond ."); break; // must be Terrain1 TO BE IMPLEMENTED
        case 2: t=new Terrain2(T,A,M,"2.- GMonitor, 1 cond/cell:"); break; // must be Terrain2 TO BE IMPLEMENTED
        case 3: t=new Terrain3(T,A,M,"3.- GMonitor, 1 cond/cell, no deadlock:");        // must be Terrain3 TO BE IMPLEMENTED
        }
