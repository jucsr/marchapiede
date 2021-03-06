package br.UFSC.GRIMA.integracao;

import java.util.Vector;

import jsdai.SMachining_schema.AExecutable;
import jsdai.SMachining_schema.AMachining_workingstep;
import jsdai.SMachining_schema.EMachining_feature;
import jsdai.SMachining_schema.EMachining_workingstep;
import jsdai.lang.SdaiException;
import jsdai.lang.SdaiIterator;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;

public class WorkingStepsReader {

	private AMachining_workingstep aMachining_workingstep;
	private EMachining_workingstep eMachining_workingstep;
	private int size;
	private int index;
	
	public WorkingStepsReader(AMachining_workingstep aMachining_workingstep){
		this.aMachining_workingstep = aMachining_workingstep;
	}
	
	public EMachining_workingstep get(int i) throws SdaiException {
		index = i+1;
		eMachining_workingstep = aMachining_workingstep.getByIndex(index);
		return eMachining_workingstep;
	}

	public int getSize() throws SdaiException {
		SdaiIterator iterator = aMachining_workingstep.createIterator();
		size = 0;
		while(iterator.next()){
			size++;
		}
		return size;
	}
	
	public static Vector<Workingstep> getWorkingsteps(AExecutable aMachining_workingstep) throws SdaiException
	{
		Vector<Workingstep> workingsteps = new Vector<Workingstep>();
		SdaiIterator iterator = aMachining_workingstep.createIterator();
		
		EMachining_feature eFeature = null;
		Feature feature = null;

		while(iterator.next()){
			
			EMachining_workingstep eMachining_workingstep = (EMachining_workingstep)aMachining_workingstep.getCurrentMember(iterator);

			
			if((EMachining_feature)eMachining_workingstep.getIts_feature(null)!= eFeature){
			
				eFeature = (EMachining_feature)eMachining_workingstep.getIts_feature(null); 
				feature = FeatureReader.getFeature(eMachining_workingstep);
			}	
				
				Face face = FaceReader.getFace(eFeature);
				feature.setFace(face);
				Ferramenta ferramenta = FerramentaReader.getFerramenta(eMachining_workingstep);
				CondicoesDeUsinagem condicoes = CondicoesDeUsinagemReader.getCondicoes(eMachining_workingstep);
				MachiningOperation operation = MachiningOperationReader.getOperation(eMachining_workingstep);
				
				Workingstep wsTmp = new Workingstep( feature , face, ferramenta, condicoes, operation);
				
				feature.getWorkingsteps().add(wsTmp);
				
				String wsId = eMachining_workingstep.getIts_id(null);
				wsTmp.setId(wsId);
				
				String[] arrayId = wsId.split("_");
				int last = arrayId.length-1;
				String wsTipo = arrayId[last];
				
				if(wsTipo.equals("RGH")){
					
					wsTmp.setTipo(Workingstep.DESBASTE);
					
				}else if(wsTipo.equals("FNS")){
					
					wsTmp.setTipo(Workingstep.ACABAMENTO);
					
				}else{
					System.out.println("Tipo de Ws desconhecido: " + wsTipo);
				}
				
				
				//Victor está setando
				
//				Ponto[] pontosIniciais = MapeadoraDeWorkingstep.determinadorDePontos(wsTmp);
//				
//				wsTmp.setPontos(pontosIniciais);
//				
//				Vector movimentacao = DeterminarMovimentacao
//				.getPontosMovimentacao(wsTmp); // alguns vetores (4 vetores)
//				
//				wsTmp.setPontosMovimentacao(movimentacao);

				workingsteps.add(wsTmp);
		}
		
		return workingsteps;
	}
	
}
