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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("webapi/producto")
public class ProductoController {

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
    public ResponseEntity<Object> guardar(@RequestBody Producto producto){

        try{
            Producto productoNuevo =productoService.guardar(producto);
            producto.setCodigo(productoNuevo.getCodigo());
            producto.getImagenesByCodigo().stream().forEach( imagen ->{
                imagen.setProductosByCodigoProducto(productoNuevo);
            });

            imagenService.guardarListaImg((List<Imagen>) producto.getImagenesByCodigo());

           return new ResponseEntity<>(productoNuevo,HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<>(e.getCause(),HttpStatus.CONFLICT);

        }


    }


    @GetMapping("/hello")
    ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);

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
 }
