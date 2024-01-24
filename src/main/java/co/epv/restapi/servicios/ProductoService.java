package co.epv.restapi.servicios;

import co.epv.restapi.entidades.Producto;
import co.epv.restapi.repositorios.ProductoRepository;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {


    private ProductoRepository productoRepository;

    @Autowired
     ProductoService(ProductoRepository productoRepository){
         this.productoRepository = productoRepository;
     }


     public List<Producto> listado (){
        return productoRepository.findAll();

     }

     public Producto guardar(Producto producto){
        return productoRepository.save(producto);
     }


    public void eliminarProducto(Integer codigo) {

        productoRepository.deleteById(codigo);

    }

    public Producto buscarProducto(Integer codigo){

        return productoRepository.findById(codigo).get();
    }

    public Producto actualizar(Producto producto){
        return productoRepository.saveAndFlush(producto);
    }
}
