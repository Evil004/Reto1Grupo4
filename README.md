# Programa para la gestion del organigrama empresarial

## Instalacion y ejecucion
Para instalar y ejecutar el programa hay 2 maneras, clonando el 
codigo fuente o descargando el archivo ejecutable ya compilado.

### Ejecutar con el coodigo fuente
Para ejecutar con el codigo fuente ejecutamos los siguientes comandos:

Linux:
~~~ shell
git clone https://github.com/Evil004/Reto1Grupo4
cd Reto1Grupo4
./run.sh
~~~

Windows (Asegurate de tener un terminal con 166 columnas o mas):
~~~ shell
git clone https://github.com/Evil004/Reto1Grupo4
cd Reto1Grupo4
./run.bat
~~~

### Ejecutar programa ya compilado
Acceder a este [enlace](https://github.com/Evil004/Reto1Grupo4/releases) y descargar el .zip con el programa ya compilado.

Una vez descargado hay que decomprimir la carpeta, entramos en la carpeta y contendra una carpeta, un archivo ejecutable 
.jar y 2 scripts, uno para windows y otro para linux, en la carpeta llamada "CSVs" introducimos los csv con los datos, y 
ejecutamos el programa ejecutando el script que corresponda con nuestro SO.

#### En windows
Para ejecutar el programa en windows usamos el script startWindows.bat, que con darle clic derecho o ejecutarlo desde una
consola se iniciara.

#### En Linux
Para ejecutar el programa en Linux usamos el script startLinux.sh, que para ejecutarlo primero tenemos que darle permisos de 
ejecucion, para ello abrimos una terminal en esta carpeta y ejecutamos el siguiente comando:
~~~ shell
chmod u+x ./startLinux.sh #Le da permiso de ejecucion al usuario
~~~

Y para ejecutar el script y el programa usamos:
~~~ shell
./startLinux.sh
~~~


## Relacion

| Miguel | Programacion |
| -- | -- |
| 1 | 1.1.2 |
| 2 | 1.1.3 |
| 3 | 1.2.1 |
| 4 | 1.1.4 |
| 5 | 1.1.5 |
| 6 | 1.3.1 |
| 7 | 1.2.2 |
| 8 | 1.3.2 |
| 9 | 2.1* |
| 10 | 2.1* |
| 11 | 2.2 |
| 12 | 3.1** |
| 13 | 3.1** |
| 14 | 3.2 |
| 15 | 3.3 |

*2.1: Están en una misma opcion debido a que nosotros no hemos separado los datos de la empresa
y los datos personales.

**3.1: Están en una misma opcion debido a que nosotros no hemos separado los datos de la empresa
y los datos personales.