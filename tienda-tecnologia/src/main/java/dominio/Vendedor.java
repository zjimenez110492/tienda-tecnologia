package dominio;
//
import dominio.repositorio.RepositorioProducto;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import dominio.repositorio.RepositorioGarantiaExtendida;


public class Vendedor {

	Scanner sc = new Scanner(System.in);
    public static final String EL_PRODUCTO_TIENE_GARANTIA = "El producto ya cuenta con una garantia extendida";

    private RepositorioProducto repositorioProducto;
    private RepositorioGarantiaExtendida repositorioGarantia;

    public Vendedor(RepositorioProducto repositorioProducto, RepositorioGarantiaExtendida repositorioGarantia) {
        this.repositorioProducto = repositorioProducto;
        this.repositorioGarantia = repositorioGarantia;
        
    }
/*
 * 
 * */  
    public void generarGarantia(String codigo) {
    	//Codigo del producto y nombre de la persona que va a comprar la garantia, 
    	//en nombre cliente de garantia extendida 
    	System.out.println("##############################");
    	GarantiaExtendida ge=  repositorioGarantia.obtener(codigo);
    	if(ge!=null)
    	System.out.println("############################   Garantia Extendida: Nombre: "+ge.getNombreCliente()+"  Precio:  "+ge.getPrecioGarantia());
    	else
    		System.out.println("Es nulo");
    	Date fechaLimite=calcularFechafin(200);
    	
    	System.out.println("*******Fecha actual m�s 200 dias es:  "+fechaLimite.toString());
    	
    }
    //Calcula los dias que dura la garantia, a partir de la fecha actual y sin tener en cuenta los lunes
private Date calcularFechafin(double precio) {
	
	int numLunes=0, dias=calcularDias(precio);
	double valorGarantia=calcularGarantia(precio);
	//NO ES LA FECHA ACTUAL, ES LA FECHA DE SOLICITUD DE LA GARANTIA
	Date fecha= new Date();
	Calendar calendar = Calendar.getInstance();
	if (dias==0) return fecha;
	else
	{			         
	    for(int i=0;i<dias;i++)
	    {	
	    	if(calendar.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY)	    		
	    	{	calendar.add(calendar.DATE, 1);}	    		
	    	else
	    	{	calendar.add(calendar.DATE, 1);
	    		dias--;
	    	}
	    }
	    if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
	    {
	    	calendar.add(calendar.DATE, 1);
	    }
	    return calendar.getTime(); 		
	}
 }
	private double calcularGarantia(double precio) {
		return precio>5000? (precio*0.2):(precio*0.1);
	}
	private int calcularDias(double precio) {
	
		return precio>5000? 200:100;
	}
	//se debe hacer un JOIN con garant�as y ver si tiene garantia
//p=this.repositorioGarantia.obtenerProductoConGarantiaPorCodigo(codigo);                 ESTE FUNCIONA
    public boolean tieneGarantia(String codigo) {
    	Producto p=null;
    	GarantiaExtendida g = null;
    	double garantia;
    	String nombreCliente;
    	int diasGarantia;
    	if(!tieneVocales(codigo))
    	{
    		p=this.repositorioGarantia.obtenerProductoConGarantiaPorCodigo(codigo);
    		//g=this.repositorioGarantia.obtener(codigo);
    		System.out.println("Producto con codigo: "+codigo+"SI TIENE garantia");
    		System.out.println("Producto:  "+p.getNombre()+" Precio: "+p.getPrecio());
    		g= new GarantiaExtendida(p);
    		System.out.println("Digite nombre de la persona ");
    		nombreCliente=sc.nextLine();
    		g= new GarantiaExtendida(p, new Date(), calcularFechafin(p.getPrecio()), calcularGarantia(p.getPrecio()), nombreCliente);
    		this.repositorioGarantia.agregar(g);
    		System.out.println("$$$$$$$$$$$$$$$$$$$Garant�a agregada: ");
    		System.out.println("Nombre: "+g.getNombreCliente()+" Fecha Sol: "+g.getFechaSolicitudGarantia()+" FechaFin: "+g.getFechaFinGarantia()+" Precio Gar: "+g.getPrecioGarantia());
    		System.out.println("Producto: "+g.getProducto().getNombre()+"    Precio: "+g.getProducto().getPrecio());
    		
    	}
    	if(p==null) return false;
    	else return true;
    }
    //Si tiene 3 vocales, se debe generar una excepci�n con un mensaje
    public boolean tieneVocales(String codigo)
    {
    	int contador=0;
    	for(int i=0;i<codigo.length();i++) {
    		  if ((codigo.charAt(i)=='a') || (codigo.charAt(i)=='e') || (codigo.charAt(i)=='i') || (codigo.charAt(i)=='o') || (codigo.charAt(i)=='u')){
    		    contador++;
    		  }
    		}
    	return contador==3;
    }
}