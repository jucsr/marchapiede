package br.UFSC.GRIMA.integracao;

import javax.vecmath.Point3d;

import jsdai.SMachining_schema.ACartesian_point;
import jsdai.SMachining_schema.EBezier_curve;
import jsdai.SMachining_schema.EBlock;
import jsdai.SMachining_schema.ECartesian_point;
import jsdai.SMachining_schema.EGeneral_profile;
import jsdai.SMachining_schema.ELinear_path;
import jsdai.SMachining_schema.EPlus_minus_value;
import jsdai.SMachining_schema.ESlot;
import jsdai.lang.SdaiException;
import jsdai.lang.SdaiIterator;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilBezier;

public class RanhuraPerfilBezierReader {

	public static RanhuraPerfilBezier getRanhura(ESlot slot) throws SdaiException {
		
		String id = slot.getIts_id(null);

		double locX = slot.getFeature_placement(null).getLocation(null)
		.getCoordinates(null).getByIndex(1);
		double locY = slot.getFeature_placement(null).getLocation(null)
		.getCoordinates(null).getByIndex(2);
		double locZ = slot.getFeature_placement(null).getLocation(null)
		.getCoordinates(null).getByIndex(3);

		double alturaBloco = ((EBlock)slot.getIts_workpiece(null).getIts_bounding_geometry(null)).getZ(null);
		
		double profundidadeRanhura = 0;
	
		ACartesian_point controlPoints = ((EBezier_curve)((EGeneral_profile)slot.getSwept_shape(null)).getIts_profile(null)).getControl_points_list(null);

		SdaiIterator iterator = controlPoints.createIterator();
		
		int j = 0;
		
		while(iterator.next()){
			j++; 
		}
		
		Point3d [] pontosDeControle = new Point3d[j]; 
		
		iterator = controlPoints.createIterator();
		
		int i = 0;
		while(iterator.next()){
			
			ECartesian_point tmpPoint = controlPoints.getCurrentMember(iterator);
			
			pontosDeControle[i] = new Point3d (tmpPoint.getCoordinates(null).getByIndex(1),tmpPoint.getCoordinates(null).getByIndex(2),tmpPoint.getCoordinates(null).getByIndex(3));
			
			i++;
			
		}
		
		Point3d ultimoPonto = pontosDeControle[i-1];
		
		double xDir = ((ELinear_path)slot.getCourse_of_travel(null)).getIts_direction(null).getDirection_ratios(null).getByIndex(1);
		double yDir = ((ELinear_path)slot.getCourse_of_travel(null)).getIts_direction(null).getDirection_ratios(null).getByIndex(2);
		double zDir = ((ELinear_path)slot.getCourse_of_travel(null)).getIts_direction(null).getDirection_ratios(null).getByIndex(3);
		
		int eixoAtual = 0;
		double x=locX;
		double y=locY;
		double z= alturaBloco - locZ;;
		double larguraRanhura = 0;
		
		if(xDir==1 && yDir==0 && zDir==0){
			eixoAtual = Ranhura.HORIZONTAL;
			larguraRanhura = ultimoPonto.getX();
		}else if (xDir==0 && yDir==1 && zDir==0) {
			eixoAtual = Ranhura.VERTICAL;
			larguraRanhura = ultimoPonto.getY();
		}else{
			System.out.println("Eixo Desconhecido: "+"( "+xDir+" , "+yDir+" , "+zDir+" )");
			
		}
		
		BlocoReader reader = new BlocoReader(slot.getIts_workpiece(null));
		
		Bloco bloco = reader.getBloco();
		
		double comprimentoRanhura = 0;
		
		switch(reader.getFaceFeature(slot)){

		case Face.XY:
			if(eixoAtual==Ranhura.HORIZONTAL)
				comprimentoRanhura = bloco.getComprimento();
			else if(eixoAtual==Ranhura.VERTICAL)
				comprimentoRanhura = bloco.getLargura();
			break;
		
		case Face.YX:
			if(eixoAtual==Ranhura.HORIZONTAL)
				comprimentoRanhura = bloco.getComprimento();
			else if(eixoAtual==Ranhura.VERTICAL)
				comprimentoRanhura = bloco.getLargura();
			break;	
		
		case Face.XZ:
			if(eixoAtual==Ranhura.HORIZONTAL)
				comprimentoRanhura = bloco.getComprimento();
			else if(eixoAtual==Ranhura.VERTICAL)
				comprimentoRanhura = bloco.getProfundidade();
			break;
		
		case Face.ZX:
			if(eixoAtual==Ranhura.HORIZONTAL)
				comprimentoRanhura = bloco.getComprimento();
			else if(eixoAtual==Ranhura.VERTICAL)
				comprimentoRanhura = bloco.getProfundidade();
			break;
		
		case Face.ZY:
			if(eixoAtual==Ranhura.HORIZONTAL)
				comprimentoRanhura = bloco.getProfundidade();
			else if(eixoAtual==Ranhura.VERTICAL)
				comprimentoRanhura = bloco.getLargura();
			break;
		
		case Face.YZ:
			if(eixoAtual==Ranhura.HORIZONTAL)
				comprimentoRanhura = bloco.getProfundidade();
			else if(eixoAtual==Ranhura.VERTICAL)
				comprimentoRanhura = bloco.getLargura();
			break;
		
		default:
			System.out.println(" FACE DESCONHECIDA !!! (BEZIER)");
			break;
		}
		
		double tolerancia = ((EPlus_minus_value)((ELinear_path)slot.getCourse_of_travel(null)).getDistance(null).getImplicit_tolerance(null)).getUpper_limit(null);
				
		RanhuraPerfilBezier ranhura = new RanhuraPerfilBezier(id, x, y, z,locX,locY,locZ,larguraRanhura,profundidadeRanhura,eixoAtual,pontosDeControle);
		ranhura.setTolerancia(tolerancia);
		ranhura.setComprimento(comprimentoRanhura);

		return ranhura;
	}

}
