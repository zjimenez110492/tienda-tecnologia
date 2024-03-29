package dominio.unitaria;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Test;

import dominio.Vendedor;
import dominio.GarantiaExtendida;
import dominio.Producto;
import dominio.repositorio.RepositorioProducto;
import dominio.repositorio.RepositorioGarantiaExtendida;
import testdatabuilder.ProductoTestDataBuilder;

public class VendedorTest {

	@Test
	public void productoYaTieneGarantiaTest() {
		
		// arrange
		ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder();		
		Producto producto = productoTestDataBuilder.build(); 
		System.out.println("\n\n\n\nENTRANDO YA TIENE GARANTIA\nProducto testeable en productoYaTieneGarantiaTest   Nombre: "+producto.getNombre()+"Precio:  "+producto.getPrecio()+"\n\n");
		
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);
		GarantiaExtendida ge= new GarantiaExtendida(producto, new Date(),vendedor.calcularFechafin(producto.getPrecio()) , vendedor.calcularGarantia(producto.getPrecio()), "Jhonatan Zuniga");
		
		when(repositorioGarantia.obtener(producto.getCodigo())).thenReturn(ge);				
		when(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo())).thenReturn(producto);		
		
		boolean existeProducto = vendedor.tieneGarantia(producto.getCodigo());
		System.out.println("Estoy en productoYaTieneGarantiaTest. Despues del Test existe Producto:"+producto.getNombre()+"?  "+existeProducto);
		System.out.println("SALIENDO YA TIENE GARANTIA \n\n\n\n\nA");
		//assert
		assertTrue(existeProducto);
	}
	
	@Test
	public void productoNoTieneGarantiaTest() {
		System.out.println("\n\n\nEntrando a No tiene garantia TEST");
		// arrange
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();
		
		Producto producto = productoestDataBuilder.build(); 
		
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);
		when(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo())).thenReturn(null);
		
		
		// act 
		boolean existeProducto =  vendedor.tieneGarantia(producto.getCodigo());
		System.out.println("Existe Garantia del producto: "+producto.getNombre()+"?   "+existeProducto);
		
		//assert
		System.out.println("SALIENDO DE NO TIENE GARANTIA\n\n\n\n");
		assertFalse(existeProducto);
		
	}
}
