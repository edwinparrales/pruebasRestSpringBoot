package co.epv.restapi.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "imagenes", schema = "webapp")
public class Imagen {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "rowid")
    private Integer rowid;
    @Basic
    @Column(name = "url")
    private String url;

    @Basic
    @Column(name = "estado")
    private int estado;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "codigo_producto", referencedColumnName = "codigo", nullable = false,updatable = true,insertable = true)
    private Producto productosByCodigoProducto;

    public Integer getRowid() {
        return rowid;
    }

    public void setRowid(Integer rowid) {
        this.rowid = rowid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }




    public Producto getProductosByCodigoProducto() {
        return productosByCodigoProducto;
    }

    public void setProductosByCodigoProducto(Producto productosByCodigoProducto) {
        this.productosByCodigoProducto = productosByCodigoProducto;
    }
}
