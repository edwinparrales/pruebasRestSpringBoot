package co.epv.restapi.controladores;

import co.epv.restapi.entidades.Imagen;
import co.epv.restapi.entidades.Producto;
import co.epv.restapi.servicios.ImagenService;
import co.epv.restapi.servicios.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("api/producto")
public class ProductoController {
    private final Path rootFolder = Paths.get("src/main/resources/static/uploads");

    @Autowired
    private ProductoService productoService;
    @Autowired
    private ImagenService imagenService;



    @GetMapping("/lista")
    public ResponseEntity<List<Producto>> listarProductos(){

        List<Producto> productos =  productoService.listado();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
/*
    @PostMapping("/guardar")
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto){

        Producto productoNuevo = productoService.guardar(producto);
        List<Imagen> listadoImagenes = (List<Imagen>) producto.getImagenesByCodigo();
        listadoImagenes.stream().forEach(imagen -> {
              imagen.setCodigoProducto(productoNuevo.getCodigo());
              imagenService.guardar(imagen);
        });


       return new ResponseEntity<>(productoNuevo,HttpStatus.OK);


    }
    */

    @PostMapping("/guardar")
    public ResponseEntity<Object> guardar(@RequestParam("producto") String producto,@RequestPart("files") List<MultipartFile> files) throws Exception{



        Map<String,Object> productoMap = new ObjectMapper().readValue(producto, HashMap.class);






        Producto producto1 = new Producto();
        producto1.setNombre(productoMap.get("nombre").toString());
        producto1.setCodigoBarras(productoMap.get("codigoBarras").toString());
        producto1.setMarca(productoMap.get("marca").toString());
        producto1.setModelo(productoMap.get("modelo").toString());
        producto1.setValor(BigDecimal.valueOf(Double.valueOf(productoMap.get("valor").toString())));
        producto1.setCantidad(Integer.valueOf(productoMap.get("cantidad").toString()));
        producto1.setEstado(Byte.parseByte(productoMap.get("estado").toString()));
        producto1.setCategoria(productoMap.get("categoria").toString());
        producto1.setPresentacion(productoMap.get("presentacion").toString());




        Producto productoNuevo =productoService.guardar(producto1);

        List<Imagen> imagenes = new ArrayList<>();
        try{


            files.stream().forEach(doc ->{
                Imagen imagen = new Imagen();
                try {

                    imagen.setProductosByCodigoProducto(productoNuevo);
                    imagen.setEstado(1);
                    imagen.setUrl(this.rootFolder.resolve(doc.getOriginalFilename()).toString());
                    imagenes.add(imagen);
                   // imagenService.guardar(imagen);
                    cargarDocumentoServidor(doc,doc.getOriginalFilename().toString());

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            System.out.println(imagenes.size());

            imagenService.guardarListaImg(imagenes);



           return new ResponseEntity<>(productoNuevo,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(e.getCause(),HttpStatus.CONFLICT);

        }


    }



    //metodo desde el formulario

    @PostMapping("/guardarForm")
    public ResponseEntity<Object> guardarFrom(@ModelAttribute Producto producto,@RequestPart("files") List<MultipartFile> files) throws Exception{


        Producto productoNuevo =productoService.guardar(producto);


        List<Imagen> imagenes = new ArrayList<>();
        try{


            files.stream().forEach(doc ->{
                Imagen imagen = new Imagen();
                try {
                    String nombreArchivo = doc.getOriginalFilename().substring(0,doc.getOriginalFilename().lastIndexOf("."));
                    String extensionArchivo = doc.getOriginalFilename().substring(doc.getOriginalFilename().lastIndexOf("."));
                    String nombreArchivoFinal = UUID.randomUUID()+extensionArchivo;

                    imagen.setProductosByCodigoProducto(productoNuevo);
                    imagen.setEstado(1);
                    imagen.setUrl("http://localhost:8080/uploads/"+nombreArchivoFinal);
                    imagenes.add(imagen);
                    // imagenService.guardar(imagen);
                    cargarDocumentoServidor(doc,nombreArchivoFinal);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            System.out.println(imagenes.size());

            imagenService.guardarListaImg(imagenes);



            return new ResponseEntity<>(productoNuevo,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(e.getCause(),HttpStatus.CONFLICT);

        }


    }















    @DeleteMapping("/eliminar/{codigo}")

    public ResponseEntity<Object> eliminar(@PathVariable Integer codigo){

        try {

             Producto producto = productoService.buscarProducto(codigo);

             imagenService.eliminarImagenes((List<Imagen>) producto.getImagenesByCodigo());

            productoService.eliminarProducto(codigo);
            return new ResponseEntity<>("Producto Eliminado",HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("Error al eliminar el producto"+e.getCause(),HttpStatus.CONFLICT);

        }

    }

    @PutMapping("/actualizar")
    public ResponseEntity<Object> actualizar(@RequestBody Producto producto){
        try{
            return  ResponseEntity.status(HttpStatus.CREATED)
                    .body(productoService.actualizar(producto));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el producto");
        }


    }

    @PostMapping("/upload")

    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws Exception{

       try {
           String nombre = file.getOriginalFilename();
           Files.copy(file.getInputStream(),this.rootFolder.resolve(file.getOriginalFilename()));
           return ResponseEntity.ok("Archivo cargado: "+nombre);
       }catch (Exception e){
           new Exception("Error");
           return ResponseEntity.ok("ddd");
       }



    }



     private  void cargarDocumentoServidor(MultipartFile file,String nombreFinalArchivo) throws Exception{

        try {

        /*     String nombreArchivo = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
             String extensionArchivo = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
             String nombreArchivoFinal = UUID.randomUUID()+extensionArchivo;
             System.out.println(nombreArchivo);
             System.out.println(extensionArchivo);*/


            Files.copy(file.getInputStream(),this.rootFolder.resolve(nombreFinalArchivo));

        }catch (Exception e){
            new Exception("Error");

        }



    }


 }
