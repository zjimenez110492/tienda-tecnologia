package persistencia.repositorio;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import dominio.Producto;
import dominio.GarantiaExtendida;
import dominio.repositorio.RepositorioProducto;
import dominio.repositorio.RepositorioGarantiaExtendida;
import persistencia.builder.ProductoBuilder;
import persistencia.entitad.ProductoEntity;
import persistencia.entitad.GarantiaExtendidaEntity;
import persistencia.repositorio.jpa.RepositorioProductoJPA;

public class RepositorioGarantiaPersistente implements RepositorioGarantiaExtendida {

	private static final String CODIGO = "codigo";
	//SELECT garantia from GarantiaExtendida garantia where garantia.producto.codigo = :codigo
	private static final String GARANTIA_EXTENDIDA_FIND_BY_CODIGO = "GarantiaExtendida.findByCodigo";

	private EntityManager entityManager;

	private RepositorioProductoJPA repositorioProductoJPA;

	public RepositorioGarantiaPersistente(EntityManager entityManager, RepositorioProducto repositorioProducto) {
		this.entityManager = entityManager;
		this.repositorioProductoJPA = (RepositorioProductoJPA) repositorioProducto;
	}

	@Override
	public void agregar(GarantiaExtendida garantia) {
		System.out.println("\n\n\n\n$$$$$$$$$$$$$$%%%%%%%%%%%%%%%%%%%%%%%%%Entrando a RepositorioGarantiaPersistente-agregar.    ");
		
		GarantiaExtendidaEntity garantiaEntity = buildGarantiaExtendidaEntity(garantia);
		entityManager.persist(garantiaEntity);
		System.out.println("\n\n\n\n");
		
	}
	
	@Override
	public Producto obtenerProductoConGarantiaPorCodigo(String codigo) {

		GarantiaExtendidaEntity garantiaEntity = obtenerGarantiaEntityPorCodigo(codigo);
				return ProductoBuilder.convertirADominio(garantiaEntity != null ? garantiaEntity.getProducto() : null);
	}
	
	@SuppressWarnings("rawtypes")
	private GarantiaExtendidaEntity obtenerGarantiaEntityPorCodigo(String codigo) {

		Query query = entityManager.createNamedQuery(GARANTIA_EXTENDIDA_FIND_BY_CODIGO);
		query.setParameter(CODIGO, codigo);

		List resultList = query.getResultList();
		return !resultList.isEmpty() ? (GarantiaExtendidaEntity) resultList.get(0) : null;
	}

	private GarantiaExtendidaEntity buildGarantiaExtendidaEntity(GarantiaExtendida garantia) {

		ProductoEntity productoEntity = repositorioProductoJPA.obtenerProductoEntityPorCodigo(garantia.getProducto().getCodigo());

		GarantiaExtendidaEntity garantiaEntity = new GarantiaExtendidaEntity();
		garantiaEntity.setProducto(productoEntity);
		garantiaEntity.setFechaSolicitudGarantia(garantia.getFechaSolicitudGarantia());

		return garantiaEntity;
	}

	
	@Override
	public GarantiaExtendida obtener(String codigo) {
		GarantiaExtendidaEntity garantiaEntity = obtenerGarantiaEntityPorCodigo(codigo);

		return new GarantiaExtendida(ProductoBuilder.convertirADominio(garantiaEntity.getProducto()),
				garantiaEntity.getFechaSolicitudGarantia(),
				garantiaEntity.getFechaFinGarantia(),
				garantiaEntity.getPrecio(),
				garantiaEntity.getNombreCliente()
				);
	}

	
	
}
