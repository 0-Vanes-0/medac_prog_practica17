# medac_prog_practica17
Autores: Ivan y Konstantin

Structura: 2 clases Meme y SaveLoadManager, 2 formas MainForm y CreateForm.

Meme:
  Tiene atributos nombre, anyoOrigen, popularidad, url y esImagen.

SaveLoadManager:
  Sirve para guardar los datos de aplicacion. Se crea el fichero data.txt en carpeta del proyecto. Si se cambia algo en la tabla de MainForm, un dialogo propone guradar datos.

MainForm:
  Al principio, descarga info de data.txt y muestra la interfaz con:
    MenuBar que tiene Fichero y Ayuda
    Label
    Table con datos de memes
    3 botones para crear nueva fila, para modificar una fila y para eliminar una fila

CreateForm:
  Usada para crear nuevo elemento o modificar uno desde la tabla
