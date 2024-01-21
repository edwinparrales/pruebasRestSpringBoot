package co.epv.restapi.entidades;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "productos", schema = "webapp", catalog = "")
public class Producto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "codigo")
    private Integer codigo;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "codigo_barras")
    private String codigoBarras;
    @Basic
    @Column(name = "marca")
    private String marca;
    @Basic
    @Column(name = "modelo")
    private String modelo;
    @Basic
    @Column(name = "valor")
    private BigDecimal valor;
    @Basic
    @Column(name = "cantidad")
    private int cantidad;
    @Basic
    @Column(name = "estado")
    private byte estado;
    @Basic
    @Column(name = "categoria")
    private String categoria;
    @Basic
    @Column(name = "presentacion")
    private String presentacion;
    @OneToMany(mappedBy = "productosByCodigoProducto")
    private Collection<Imagen> imagenesByCodigos;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public byte getEstado() {
        return estado;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return codigo == producto.codigo && cantidad == producto.cantidad && estado == producto.estado && Objects.equals(nombre, producto.nombre) && Objects.equals(codigoBarras, producto.codigoBarras) && Objects.equals(marca, producto.marca) && Objects.equals(modelo, producto.modelo) && Objects.equals(valor, producto.valor) && Objects.equals(categoria, producto.categoria) && Objects.equals(presentacion, producto.presentacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nombre, codigoBarras, marca, modelo, valor, cantidad, estado, categoria, presentacion);
    }

    public Collection<Imagen> getImagenesByCodigo() {
        return imagenesByCodigos;
    }

    public void setImagenesByCodigo(Collection<Imagen> imagenesByCodigos) {
        this.imagenesByCodigos = imagenesByCodigos;
    }
}
