package co.epv.restapi.modelo;

import co.epv.restapi.entidades.Producto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductoModel {
    private Producto producto;
    private List<MultipartFile> multipartFileList;

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<MultipartFile> getMultipartFileList() {
        return multipartFileList;
    }

    public void setMultipartFileList(List<MultipartFile> multipartFileList) {
        this.multipartFileList = multipartFileList;
    }
}
