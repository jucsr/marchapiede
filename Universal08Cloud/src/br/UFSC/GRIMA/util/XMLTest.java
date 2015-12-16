package br.UFSC.GRIMA.util;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLTest 
{
	public static void main(String[] args)
	{
		ArrayList<Point2D> vertex = new ArrayList<Point2D>();
		vertex.add(new Point2D.Double(0, 0));
		vertex.add(new Point2D.Double(100, 0));
		vertex.add(new Point2D.Double(100, 50));
		vertex.add(new Point2D.Double(0, 50));
		GeneralClosedPocket general = new GeneralClosedPocket(5, vertex);
		general.setNome("teste");
		XStream xstream = new XStream(new DomDriver());
		String saida = xstream.toXML(general);
		System.out.println(saida);
		
		XStream xStream2 = new XStream(new DomDriver());
		xStream2.alias("general", GeneralClosedPocket.class);
		GeneralClosedPocket general2 = (GeneralClosedPocket)xStream2.fromXML(saida);
		System.err.println("radius = " +  general2.getRadius());
		System.err.println("name = " + general2.getNome());
		System.err.println("vertices = " + general2.getVertexPoints());
//		System.err.println(general2.getFace());
	}
}