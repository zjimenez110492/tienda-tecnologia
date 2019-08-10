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
    	    	
    	String nombreCliente;
    	GarantiaExtendida g= null;
    	Producto p=this.repositorioProducto.obtenerPorCodigo(codigo);
    	System.out.println("********************* ENTRANDO AL METODO GENERAR GARANTIA");
    	if(!tieneGarantia(codigo)&&p!=null)
    	{
    		System.out.println("\n\nEl producto "+p.getNombre()+" aun no posee una Garantia. \nDigite nombre de la persona que va a comprar la garantia");
    		nombreCliente=sc.nextLine();
    		g= new GarantiaExtendida(p, new Date(), calcularFechafin(p.getPrecio()), calcularGarantia(p.getPrecio()), nombreCliente);
    		this.repositorioGarantia.agregar(g);
    		System.out.println("$$$$$$$$$$$$$$$$$$$Garant�a agregada: ");
    		System.out.println("Nombre: "+g.getNombreCliente()+" Fecha Sol: "+g.getFechaSolicitudGarantia()+" FechaFin: "+g.getFechaFinGarantia()+" Precio Gar: "+g.getPrecioGarantia());
    		System.out.println("Producto: "+g.getProducto().getNombre()+"    Precio: "+g.getProducto().getPrecio()+"\n\n\n");
	
    	}
    	else
    	{
    		g=this.repositorioGarantia.obtener(codigo);
    		System.out.println("\n\n\nLO SENTIMOS..!  El producto "+p.getNombre()+" ya posee una Garant�a");
    		System.out.println("Garantia: Nombre Cliente"+g.getNombreCliente()+" Precio Garantia:"+g.getPrecioGarantia()+"  Fecha Fin Garantia: "+g.getFechaFinGarantia()+"\n\n\n");
    	}
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
    	System.out.println("********************* ENTRANDO AL METODO TIENE GARANTIA");
    	
    	Producto p=null;
    	GarantiaExtendida g = null;
    	double garantia;
    	int diasGarantia;
    	boolean tieneGarantia=false;
    	String nombre="";
    	
    	if(!comprobarVocales(codigo))
    	{
    		p=this.repositorioProducto.obtenerPorCodigo(codigo);
    		g=this.repositorioGarantia.obtener(codigo);
    		if(g!=null&&p!=null)
    		{	//System.out.println("EL PRODUCTO SI XISTE, AUN NO SE COMPRUEBA QUE SI TIENE GARANTIA");
    		//System.out.println("Datos del producto: Nombre: "+p.getNombre()+" Prcio:  "+p.getPrecio());
    		//Compruebo que tenga garantia
    			nombre=p.getNombre();
    			p=this.repositorioGarantia.obtenerProductoConGarantiaPorCodigo(codigo);
    			if(p!=null)
    				{tieneGarantia=true;}
    			
    		}
    		
    	}
    	else
    	{System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    	System.out.println("$$$$$$$$$$$$$**********************$$$$$$$$$$$$$$$$$$$$$");
    		System.out.println("FALTA MANDAR LA EXCEPCION DE QUE EL PRODUCTO TIENE 3 VOALES Y NO TIENE GARANTIA EXTENDIDA");
    	}
    	return tieneGarantia;
    }
    //Si tiene 3 vocales, se debe generar una excepci�n con un mensaje
    public boolean comprobarVocales(String codigo)
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
