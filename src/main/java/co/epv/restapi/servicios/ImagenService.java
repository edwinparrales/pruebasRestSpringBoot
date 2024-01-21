package co.epv.restapi.servicios;

import co.epv.restapi.entidades.Imagen;
import co.epv.restapi.repositorios.ImagenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagenService {
    @Autowired
    private ImagenRepository imagenRepository;


    public List<Imagen> listar(){
        return imagenRepository.findAll();
    }

    public Imagen guardar(Imagen imagen){

        return imagenRepository.save(imagen);
    }

    public List<Imagen> guardarListaImg(List<Imagen> imagenes){

        return imagenRepository.saveAll(imagenes);
    }

    public void eliminarImagenes(List<Imagen> imagenes){

        imagenRepository.deleteAll(imagenes);
    }
}
