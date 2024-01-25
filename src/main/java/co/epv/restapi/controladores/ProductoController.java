package co.epv.restapi.controladores;

import co.epv.restapi.entidades.Imagen;
import co.epv.restapi.entidades.Producto;
import co.epv.restapi.servicios.ImagenService;
import co.epv.restapi.servicios.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<Object> guardar(@RequestBody Producto producto,@RequestParam("files") List<MultipartFile> files) throws Exception{
        Producto productoNuevo =productoService.guardar(producto);
        Imagen imagen = new Imagen();
        try{


            files.stream().forEach(doc ->{
                try {
                    imagen.setProductosByCodigoProducto(productoNuevo);
                    imagen.setEstado(1);
                    imagen.setUrl(this.rootFolder.resolve(doc.getOriginalFilename()).toString());
                    imagenService.guardar(imagen);
                    cargarDocumentoServodor(doc);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });




            imagenService.guardarListaImg((List<Imagen>) producto.getImagenesByCodigo());

           return new ResponseEntity<>(productoNuevo,HttpStatus.CREATED);

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



     private  void cargarDocumentoServodor(MultipartFile file) throws Exception{

        try {
            String nombre = file.getOriginalFilename();
            Files.copy(file.getInputStream(),this.rootFolder.resolve(file.getOriginalFilename()));

        }catch (Exception e){
            new Exception("Error");

        }



    }


 }
