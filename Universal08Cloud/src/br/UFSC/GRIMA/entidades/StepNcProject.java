package br.UFSC.GRIMA.entidades;

import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import jsdai.SCombined_schema.*;
import jsdai.dictionary.CBoolean_type;
import jsdai.dictionary.EBoolean_type;
import jsdai.dictionary.EDefined_type;
import jsdai.lang.A_double;
import jsdai.lang.A_string;
import jsdai.lang.Aa_double;
import jsdai.lang.Aggregate;
import jsdai.lang.EEntity;
import jsdai.lang.ELogical;
import jsdai.lang.SdaiException;
import jsdai.lang.SdaiIterator;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.Boring;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.CenterDrilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.capp.machiningOperations.FreeformOperation;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.capp.machiningOperations.Reaming;
import br.UFSC.GRIMA.capp.machiningOperations.Two5DMillingOperation;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.Bidirectional;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.ContourParallel;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.ContourParallel.RotationDirection;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.TrochoidalAndContourParallelStrategy;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.Two5DMillingStrategy;
import br.UFSC.GRIMA.capp.plunge.ApproachRetractStrategy;
import br.UFSC.GRIMA.capp.plunge.PlungeHelix;
import br.UFSC.GRIMA.capp.plunge.PlungeRamp;
import br.UFSC.GRIMA.capp.plunge.PlungeStrategy;
import br.UFSC.GRIMA.capp.plunge.PlungeToolAxis;
import br.UFSC.GRIMA.capp.plunge.PlungeZigzag;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CavidadeFundoArredondado;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;
import br.UFSC.GRIMA.entidades.features.FuroBaseConica;
import br.UFSC.GRIMA.entidades.features.FuroBaseEsferica;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.FuroConico;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilBezier;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilCircularParcial;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilRoundedU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilVee;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.entidades.features.Region;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.Reamer;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;
import br.UFSC.GRIMA.util.projeto.Projeto;
/**
 * 
 * @author Jc
 *
 */
public class StepNcProject extends STEPProject
{
	//private SdaiModel model;
	//private SdaiRepository repository;
	private EWorkpiece eWorkpiece = null;
	private EPlane securityPlane;
	private double globalTolerance;
	private double distanceUpperSurfaceWorkpiece = 25;
	private double workpieceLength, workpiecewidth, workpieceHight;
	private double pocketTolerance = 0.01;
	private ArrayList<ArrayList<Double>> featurePlacement;
	private ArrayList<Double> featureLocation;
	private ArrayList<Double> featureAxis;
	private ArrayList<Double> featureRefDirection;
	private EAxis2_placement_3d workpiecePlacement;
	private EWorkplan eWorkplan;
	//private EProject eProject;
	private double toolLength, toolTipAngle = 0 * Math.PI / 180, toolDiameter = 20, toolEdgeCuttingLength = 10;
	private int toolHandOfCut = EHand.RIGHT;
	private int toolNumberOfTeeth = 4;
	private double feedRate = 0.04, spindleRotation = 12.0, toolAp = 0, toolAe = 0, vc;
	private ArrayList<Point3d> clampingPoints = new ArrayList<Point3d>();
	private Vector workigsteps;
	public Projeto projeto;
	//private SdaiSession session;
	private String owner;
	private ArrayList<Feature> usedFeatures = new ArrayList<Feature>();
	private AMachining_operation operations;
	private ArrayList<Ferramenta> usedTools = new ArrayList<Ferramenta>();
	
	public StepNcProject (Vector workingsteps, Projeto projeto) throws SdaiException 
	{
		super();
		this.workigsteps = workingsteps;
		this.projeto = projeto;
		/**
		 *  steps for creating a new pocket
		 *  1. create a project (setProject();) -- this will create the workpiece, block, etc... // this shall be done only one time
		 *  2. create a setup (createSetup();) // this shall be done one time
		 *  3. create the operations related with the pocket (i.e. roughing, finishing... with createOperations();)
		 *  4. create the closed pocket (createClosedPocket();)
		 *  5. create the workplan (createWorkplan();)
		 *  
		 *  ready!
		 *  
		 */
		//setting the header data
		
		this.createHeaderData();
		
		
		// === setting a block ===
		this.workpieceLength = this.projeto.getBloco().getComprimento();
		this.workpiecewidth = this.projeto.getBloco().getLargura();
		this.workpieceHight = this.projeto.getBloco().getProfundidade();
		this.owner = this.projeto.getDadosDeProjeto().getUserName();
		// == creating clamping points ==
		
//		Rectangle3D Bloco = new Rectangle3D(this.workpieceLength, this.workpiecewidth, this.workpieceHight );
//		
//		ArrayList<Feature> arrayList = new ArrayList<Feature>();
//		Feature anterior = null;
//		
//		for (int index = 0; index < this.workigsteps.size(); index++)
//		{
//			Vector workinstepsFaceTmp = (Vector)this.workigsteps.get(index);
//			for (int i = 0; i < workinstepsFaceTmp.size(); i++)
//			{
//				Workingstep wsTmp = (Workingstep)workinstepsFaceTmp.elementAt(i);
//				if (anterior == null){
//				arrayList.add(wsTmp.getFeature());	
//				anterior = wsTmp.getFeature();
//				}
//				else{
//				if (wsTmp.getFeature().equals(anterior)){System.out.println("Feature igual nao adicionado");}
//				else {arrayList.add(wsTmp.getFeature());
//				}
//				anterior = wsTmp.getFeature();
//				}
//			}
//		}
//		
//		int raioApoio = 2;
//		
//		VValidator validator = new VValidator(Bloco, arrayList, raioApoio); 
//		ArrayList<ArrayList<Point3d>> ClampingPoints = null;
//		ArrayList<Point3d> BaseClampingPoints;
//		
//		try {
//			ClampingPoints = validator.getPoints(4, 0, 0);
//		} catch (ProjetoInvalidoException e) {
//			
//			e.printStackTrace();
//		}
//		
//		BaseClampingPoints = ClampingPoints.get(0);
//		
//		Point3d point1 = BaseClampingPoints.get(0);
//		Point3d point2 = BaseClampingPoints.get(1);
//		Point3d point3 = BaseClampingPoints.get(2);
//		Point3d point4 = BaseClampingPoints.get(3);
		
		
//		Point3d point1 = new Point3d(workpieceLength/10, workpiecewidth/10, 0);
//		Point3d point2 = new Point3d(workpieceLength * 9 /10, workpiecewidth/10, 0);
//		Point3d point3 = new Point3d(workpieceLength * 9 /10, workpiecewidth * 9 /10, 0);
//		Point3d point4 = new Point3d(workpieceLength/10, workpiecewidth * 9 /10, 0);
//		
//		this.clampingPoints.add(point1);
//		this.clampingPoints.add(point2);
//		this.clampingPoints.add(point3);
//		this.clampingPoints.add(point4);
		
//		Face face = (Face)this.projeto.getBloco().faces.elementAt(0);
//		ArrayList<Point3d> pontosDeApoio = face.getPontosDeApoio();
//		for(Point3d pontoTmp: pontosDeApoio)
//		{
//			System.out.println("PONTO APOIO = " + pontoTmp);
//			this.clampingPoints.add(pontoTmp);
//		}
		
//		for(int i = 0; i < this.projeto.getSetupsArray().size(); i++)
//		{
//			for(int j = 0; j < this.projeto.getSetupsArray().get(i).size(); j++)
//			{
//				for(int k = 0; k < this.projeto.getSetupsArray().get(i).get(j).size(); k++)
//				{
//					Point3d pontoTmp = this.projeto.getSetupsArray().get(i).get(j).get(k);
//					this.clampingPoints.add(pontoTmp);
//				}
//			}
//		}
		

		// == set a project == 
		this.setProject(this.projeto.getDadosDeProjeto().getProjectName(), this.projeto.getBloco().getToleranciaGlobal());
		
		// == creating a setup with its coordinates system ==
		ArrayList<ArrayList<Double>> origem = new ArrayList<ArrayList<Double>>();
		
		ArrayList<Double> l = new ArrayList<Double>();
		ArrayList<Double> a = new ArrayList<Double>();
		ArrayList<Double> r = new ArrayList<Double>();
		
		l.add(0, 0.0);
		l.add(1, 0.0);
		l.add(2, 0.0);
		
		a.add(0, 0.0);
		a.add(1, 0.0);
		a.add(2, 1.0);
		
		r.add(0, 1.0);
		r.add(1, 0.0);
		r.add(2, 0.0);

		origem.add(0, l);
		origem.add(1, a);
		origem.add(2, r);
		
		ESetup setup = this.createSetup("setup", origem);
		
		//== creating the machining_workinsteps ==
		
		
		AWorkplan aWorkplan = new AWorkplan();
		EMachining_workingstep eMachining_workingstep = null;
		int contador = 1;
		for (int index = 0; index < this.workigsteps.size(); index++)
		{
			AExecutable aExecutable = new AExecutable();
			Vector workinstepsFaceTmp = (Vector)this.workigsteps.get(index);
			for (int i = 0; i < workinstepsFaceTmp.size(); i++)
			{
				Workingstep wsTmp = (Workingstep)workinstepsFaceTmp.elementAt(i);
				
					 /**
					  * ****** ============> FURO COM FUNDO PLANO ======= ******
					  */	
					if (wsTmp.getFeature().getClass() == FuroBasePlana.class)
					{
						ERound_hole eRound_hole = null;
						FuroBasePlana furo = (FuroBasePlana)wsTmp.getFeature();
						AMachining_operation operationsFuro = null;
						if (this.alreadyUsed(furo) == false)
						{
//							 this.operations = new AMachining_operation(); // verificar isto, acho q vai dar pobremas mais tarde
							 operationsFuro = new AMachining_operation();
							 furo.setOperations(operationsFuro);
						} else
						{
							operationsFuro = furo.getOperations();
						}
						EMachining_operation operationHole = null;
						if((wsTmp.getOperation()).getClass() == Drilling.class)
						{
							operationHole = this.createDrillingOperation(wsTmp);
						} else if (wsTmp.getOperation().getClass() == Boring.class)
						{
							operationHole = this.createBoringOperation(wsTmp);
						} else if((wsTmp.getOperation()).getClass() == Reaming.class)
						{
							operationHole = this.createReamingOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == CenterDrilling.class)
						{
							operationHole = this.createCenterDrillingOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class)
						{
							operationHole = this.createBottomAndSideRoughOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class)
						{
							operationHole = this.createBottomAndSideFinishOperation(wsTmp);
						}
//						this.operations.addUnordered(operationHole);
						operationsFuro.addUnordered(operationHole);
						
						if (this.alreadyUsed(furo) == false)
						{
							if(furo.isPassante() == false) 
							{
//								eRound_hole = this.createFlatBottomRoundHole(wsTmp, this.eWorkpiece, this.operations);
								eRound_hole = this.createFlatBottomRoundHole(wsTmp, this.eWorkpiece, operationsFuro);
							} else 
							{
//								eRound_hole = this.createThroughBottomRoundHole(wsTmp, this.eWorkpiece, this.operations);
								eRound_hole = this.createThroughBottomRoundHole(wsTmp, this.eWorkpiece, operationsFuro);

							}
							furo.seteRound_hole(eRound_hole);
							this.usedFeatures.add(furo);
						} else
						{
							eRound_hole = furo.geteRound_hole();
							AMachining_operation aMachining_operation = eRound_hole.createIts_operations(null);
//							SdaiIterator iterator = this.operations.createIterator();
							SdaiIterator iterator = operationsFuro.createIterator();
							while(iterator.next())
							{
//								EMachining_operation eMachining_operation = this.operations.getCurrentMember(iterator);
								EMachining_operation eMachining_operation = operationsFuro.getCurrentMember(iterator);
								aMachining_operation.addUnordered(eMachining_operation);
							}						
						}
						eMachining_workingstep = this.createElements(wsTmp.getId(), this.securityPlane, eRound_hole, operationHole);
					}
					/**
					 *=================	Furo com base conica		================
					 */
					if (wsTmp.getFeature().getClass() == FuroBaseConica.class)
					{
							ERound_hole eRound_hole;
							FuroBaseConica furoConicaltmp = (FuroBaseConica)wsTmp.getFeature();
							AMachining_operation operationsFuro = null;
							if (this.alreadyUsed(furoConicaltmp) == false)
							{
//								 this.operations = new AMachining_operation();
								operationsFuro = new AMachining_operation();
								furoConicaltmp.setOperations(operationsFuro);
							} else
							{
								operationsFuro = furoConicaltmp.getOperations();
							}
							EMachining_operation operationHole = null;
							if((wsTmp.getOperation()).getClass() == Drilling.class)
							{
								operationHole = this.createDrillingOperation(wsTmp);
							} else if (wsTmp.getOperation().getClass() == Boring.class)
							{
								operationHole = this.createBoringOperation(wsTmp);
							} else if((wsTmp.getOperation()).getClass() == Reaming.class)
							{
								operationHole = this.createReamingOperation(wsTmp);
							} else if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class)
							{
								operationHole = this.createBottomAndSideRoughOperation(wsTmp);
							} else if(wsTmp.getOperation().getClass() == CenterDrilling.class)
							{
								operationHole = this.createCenterDrillingOperation(wsTmp);
							} else if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class)
							{
								operationHole = this.createBottomAndSideFinishOperation(wsTmp);
							}
//							this.operations.addUnordered(operationHole);
							operationsFuro.addUnordered(operationHole);
							if (this.alreadyUsed(furoConicaltmp) == false)
							{
//								eRound_hole = this.createConicalBottomRoundHole(wsTmp, this.eWorkpiece, this.operations);
								eRound_hole = this.createConicalBottomRoundHole(wsTmp, this.eWorkpiece, operationsFuro);
								furoConicaltmp.seteRound_hole(eRound_hole);
								this.usedFeatures.add(furoConicaltmp);
							} else
							{
								eRound_hole = furoConicaltmp.geteRound_hole();
								AMachining_operation aMachining_operation = eRound_hole.createIts_operations(null);
//								SdaiIterator iterator = this.operations.createIterator();
								SdaiIterator iterator = operationsFuro.createIterator();
								while(iterator.next())
								{
//									EMachining_operation eMachining_operation = this.operations.getCurrentMember(iterator);
									EMachining_operation eMachining_operation = operationsFuro.getCurrentMember(iterator);
									aMachining_operation.addUnordered(eMachining_operation);
								}	
							}
							eMachining_workingstep = createElements(wsTmp.getId(), this.securityPlane, eRound_hole, operationHole);
					}	
					/**
					 *   ==================		Furo com base arredondada 		===============
					 */
					else if(wsTmp.getFeature().getClass() == FuroBaseArredondada.class)
					{
						ERound_hole eRound_hole;
						FuroBaseArredondada furoBATmp = (FuroBaseArredondada)wsTmp.getFeature();
						AMachining_operation operationsFuro = null;						
						if (this.alreadyUsed(furoBATmp) == false)
						{
//							 this.operations = new AMachining_operation();
							operationsFuro = new AMachining_operation();
							furoBATmp.setOperations(operationsFuro);
						} else
						{
							operationsFuro = furoBATmp.getOperations();
						}
						EMachining_operation operationHole = null;
						if(wsTmp.getOperation().getClass() == Drilling.class)
						{
							operationHole = this.createDrillingOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == Boring.class)
						{
							operationHole = this.createBoringOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == Reaming.class)
						{
							operationHole = this.createReamingOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == CenterDrilling.class)
						{
							operationHole = this.createCenterDrillingOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class)
						{
							operationHole = this.createBottomAndSideRoughOperation(wsTmp);
						}  else if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class)
						{
							operationHole = this.createBottomAndSideFinishOperation(wsTmp);
						}
						operationsFuro.addUnordered(operationHole);
						if (this.alreadyUsed(furoBATmp) == false)
						{												
							eRound_hole = createFlatWithRadiusBottomHole(wsTmp, this.eWorkpiece, operationsFuro);
							furoBATmp.seteRound_hole(eRound_hole);
							this.usedFeatures.add(furoBATmp);
						} else
						{
							eRound_hole = furoBATmp.geteRound_hole();
							AMachining_operation aMachining_operation = eRound_hole.createIts_operations(null);
							SdaiIterator iterator = operationsFuro.createIterator();
							while(iterator.next())
							{
								EMachining_operation eMachining_operation = operationsFuro.getCurrentMember(iterator);
								aMachining_operation.addUnordered(eMachining_operation);
							}	
						}
						eMachining_workingstep = createElements(wsTmp.getId(), this.securityPlane, eRound_hole, operationHole);
					}
					/**
					 *  ============ Furo com Base Esférica ============
					 */
					else if (wsTmp.getFeature().getClass() == FuroBaseEsferica.class)
					{
						ERound_hole eRound_hole;
						FuroBaseEsferica furoBETmp = (FuroBaseEsferica)wsTmp.getFeature();
						AMachining_operation operationsFuro = null;
						if (this.alreadyUsed(furoBETmp) == false)
						{
							operationsFuro = new AMachining_operation();
							furoBETmp.setOperations(operationsFuro);
						} else
						{
							operationsFuro = furoBETmp.getOperations();
						}
						EMachining_operation operationHole = null;
						if(wsTmp.getOperation().getClass() == Drilling.class)
						{
							operationHole = this.createDrillingOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == Boring.class)
						{
							operationHole = this.createBoringOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == Reaming.class)
						{
							operationHole = this.createReamingOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == CenterDrilling.class)
						{
							operationHole = this.createCenterDrillingOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class)
						{
							operationHole = this.createBottomAndSideRoughOperation(wsTmp);
						}  else if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class)
						{
							operationHole = this.createBottomAndSideFinishOperation(wsTmp);
						}
//						this.operations.addUnordered(operationHole);
						operationsFuro.addUnordered(operationHole);
						if (this.alreadyUsed(furoBETmp) == false)
						{
//							eRound_hole = createSphericalBottomHole(wsTmp, this.eWorkpiece, this.operations);
							eRound_hole = this.createSphericalBottomHole(wsTmp, this.eWorkpiece, operationsFuro);
							furoBETmp.seteRound_hole(eRound_hole);
							this.usedFeatures.add(furoBETmp);
						} else
						{
							eRound_hole = furoBETmp.geteRound_hole();
							AMachining_operation aMachining_operation = eRound_hole.createIts_operations(null);
//							SdaiIterator iterator = this.operations.createIterator();
							SdaiIterator iterator = operationsFuro.createIterator();

							while(iterator.next())
							{
//								EMachining_operation eMachining_operation = this.operations.getCurrentMember(iterator);
								EMachining_operation eMachining_operation = operationsFuro.getCurrentMember(iterator);
								aMachining_operation.addUnordered(eMachining_operation);
							}	
						}
						eMachining_workingstep = createElements(wsTmp.getId(), securityPlane, eRound_hole, operationHole);
					}
					/**
					 *  ============ Furo Conico =============
					 */
					else if (wsTmp.getFeature().getClass() == FuroConico.class)
					{
						ERound_hole eRound_hole;
						FuroConico furoConicoTmp = (FuroConico)wsTmp.getFeature();
						AMachining_operation operationsFuro = null;
						if (this.alreadyUsed(furoConicoTmp) == false)
						{
//							 this.operations = new AMachining_operation();
							operationsFuro = new AMachining_operation();
							furoConicoTmp.setOperations(operationsFuro);
						} else
						{
							operationsFuro = furoConicoTmp.getOperations();
						}
						EMachining_operation operationHole = null;
						if(wsTmp.getOperation().getClass() == Drilling.class)
						{
							operationHole = this.createDrillingOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == Boring.class)
						{
							operationHole = this.createBoringOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == Reaming.class)
						{
							operationHole = this.createReamingOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == CenterDrilling.class)
						{
							operationHole = this.createCenterDrillingOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class)
						{
							operationHole = this.createBottomAndSideRoughOperation(wsTmp);
						}  else if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class)
						{
							operationHole = this.createBottomAndSideFinishOperation(wsTmp);
						}
//						this.operations.addUnordered(operationHole);
						operationsFuro.addUnordered(operationHole);
						if (this.alreadyUsed(furoConicoTmp) == false)
						{
//							eRound_hole = this.createConicHole(wsTmp, this.eWorkpiece, this.operations);
							eRound_hole = this.createConicHole(wsTmp, this.eWorkpiece, operationsFuro);
							furoConicoTmp.seteRound_hole(eRound_hole);
							this.usedFeatures.add(furoConicoTmp);
						} else
						{
							eRound_hole = furoConicoTmp.geteRound_hole();
							AMachining_operation aMachining_operation = eRound_hole.createIts_operations(null);
//							SdaiIterator iterator = this.operations.createIterator();
							SdaiIterator iterator = operationsFuro.createIterator();

							while(iterator.next())
							{
//								EMachining_operation eMachining_operation = this.operations.getCurrentMember(iterator);
								EMachining_operation eMachining_operation = operationsFuro.getCurrentMember(iterator);
								aMachining_operation.addUnordered(eMachining_operation);
							}	
						}
						eMachining_workingstep = this.createElements(wsTmp.getId(), securityPlane, eRound_hole, operationHole);
					}
					/**
					 *  ===================		Ranhura normal 	===============
					 */
					else if (wsTmp.getFeature().getClass() == Ranhura.class)
					{
						ESlot eSlot;
						Ranhura ranhuraTmp = (Ranhura)wsTmp.getFeature();
						AMachining_operation operationsRanhura = null;
						if (this.alreadyUsed(ranhuraTmp) == false)
						{
//							this.operations = new AMachining_operation(); // -----> isto vai dar problemas!
							operationsRanhura = new AMachining_operation();
							ranhuraTmp.setOperations(operationsRanhura);
						} else
						{
							operationsRanhura = ranhuraTmp.getOperations();
						}
						EMachining_operation operationSlot = null;
						if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class)
						{
							operationSlot = this.createBottomAndSideRoughOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class)
						{
							operationSlot = this.createBottomAndSideFinishOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == FreeformOperation.class)
						{
							operationSlot = this.createFreeFormOperation(wsTmp);
						}
//						this.operations.addUnordered(operationSlot);
						operationsRanhura.addUnordered(operationSlot);
						if(this.alreadyUsed(ranhuraTmp) == false)
						{
//							eSlot = this.createSlot(wsTmp, this.eWorkpiece, this.operations);
							eSlot = this.createSlot(wsTmp, this.eWorkpiece, operationsRanhura);
							ranhuraTmp.setESlot(eSlot);
							this.usedFeatures.add(ranhuraTmp);
						} else
						{
							eSlot = ranhuraTmp.getESlot();
							AMachining_operation aMachining_operation = eSlot.createIts_operations(null);
//							SdaiIterator iterator = this.operations.createIterator();
							SdaiIterator iterator = operationsRanhura.createIterator();
							while(iterator.next())
							{
//								EMachining_operation eMachining_operation = this.operations.getCurrentMember(iterator);
								EMachining_operation eMachining_operation = operationsRanhura.getCurrentMember(iterator);
								aMachining_operation.addUnordered(eMachining_operation);
							}	
						}
						eMachining_workingstep = this.createElements(wsTmp.getId(), securityPlane, eSlot, operationSlot);
					} 
					/**
					 *  ============ Ranhura perfil U Quadrado ============
					 */
					else if (wsTmp.getFeature().getClass() == RanhuraPerfilQuadradoU.class)
					{
						ESlot eSlot;
						RanhuraPerfilQuadradoU ranhuraTmp = (RanhuraPerfilQuadradoU)wsTmp.getFeature();
						AMachining_operation operationsRanhura = null;
						if (this.alreadyUsed(ranhuraTmp) == false)
						{
//							this.operations = new AMachining_operation();
							operationsRanhura = new AMachining_operation();
							ranhuraTmp.setOperations(operationsRanhura);
						} else
						{
							operationsRanhura = ranhuraTmp.getOperations();
						}
						EMachining_operation operationSlot = null;
						if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class)
						{
							operationSlot = this.createBottomAndSideRoughOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class)
						{
							operationSlot = this.createBottomAndSideFinishOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == FreeformOperation.class)
						{
							operationSlot = this.createFreeFormOperation(wsTmp);
						}
//						this.operations.addUnordered(operationSlot);
						operationsRanhura.addUnordered(operationSlot);
						if(this.alreadyUsed(ranhuraTmp) == false)
						{
//							eSlot = this.createSquareUProfileSlot(wsTmp, this.eWorkpiece, this.operations);
							eSlot = this.createSquareUProfileSlot(wsTmp, this.eWorkpiece, operationsRanhura);
							ranhuraTmp.setESlot(eSlot);
							this.usedFeatures.add(ranhuraTmp);
						} else
						{
							eSlot = ranhuraTmp.getESlot();
							AMachining_operation aMachining_operation = eSlot.createIts_operations(null);
//							SdaiIterator iterator = this.operations.createIterator();
							SdaiIterator iterator = operationsRanhura.createIterator();
							while(iterator.next())
							{
//								EMachining_operation eMachining_operation = this.operations.getCurrentMember(iterator);
								EMachining_operation eMachining_operation = operationsRanhura.getCurrentMember(iterator);
								aMachining_operation.addUnordered(eMachining_operation);
							}	
						}
						eMachining_workingstep = this.createElements(wsTmp.getId(), this.securityPlane, eSlot, operationSlot);
					}
					/**
					 * ============ Ranhura perfil Parcial Circular ===============
					 */
					else if (wsTmp.getFeature().getClass() == RanhuraPerfilCircularParcial.class)
					{
						ESlot eSlot;
						RanhuraPerfilCircularParcial ranhuraTmp = (RanhuraPerfilCircularParcial)wsTmp.getFeature();
						AMachining_operation operationsRanhura = null;
						if(this.alreadyUsed(ranhuraTmp) == false)
						{
							operationsRanhura = new AMachining_operation();
							ranhuraTmp.setOperations(operationsRanhura);
						} else
						{
							operationsRanhura = ranhuraTmp.getOperations();
						}
						EMachining_operation operationSlot = null;
						if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class)
						{
							operationSlot = this.createBottomAndSideRoughOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class)
						{
							operationSlot = this.createBottomAndSideFinishOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == FreeformOperation.class)
						{
							operationSlot = this.createFreeFormOperation(wsTmp);
							ranhuraTmp.setOperations(operationsRanhura);
						} else
						{
							operationsRanhura = ranhuraTmp.getOperations();
						}
						operationsRanhura.addUnordered(operationSlot);
						if(this.alreadyUsed(ranhuraTmp) == false)
						{
//							eSlot = this.createCircularPartialProfileSlot(wsTmp, this.eWorkpiece, this.operations);
							eSlot = this.createCircularPartialProfileSlot(wsTmp, this.eWorkpiece, operationsRanhura);
							ranhuraTmp.setESlot(eSlot);
							this.usedFeatures.add(ranhuraTmp);
						} else
						{
							eSlot = ranhuraTmp.getESlot();
							AMachining_operation aMachining_operation = eSlot.createIts_operations(null);
//							SdaiIterator iterator = this.operations.createIterator();
							SdaiIterator iterator = operationsRanhura.createIterator();
							while(iterator.next())
							{
//								EMachining_operation eMachining_operation = this.operations.getCurrentMember(iterator);
								EMachining_operation eMachining_operation = operationsRanhura.getCurrentMember(iterator);
								aMachining_operation.addUnordered(eMachining_operation);
							}
						}
						eMachining_workingstep = this.createElements(wsTmp.getId(), this.securityPlane, eSlot, operationSlot);
					}
					/**
					 * ============ Ranhura perfil U Circular ==============
					 */
					else if (wsTmp.getFeature().getClass() == RanhuraPerfilRoundedU.class)
					{
						ESlot eSlot;
						RanhuraPerfilRoundedU ranhuraTmp = (RanhuraPerfilRoundedU)wsTmp.getFeature();
						AMachining_operation operationsRanhura = null;
						if(this.alreadyUsed(ranhuraTmp) == false)
						{
							operationsRanhura = new AMachining_operation();
							ranhuraTmp.setOperations(operationsRanhura);
						} else
						{
							operationsRanhura = ranhuraTmp.getOperations();
						}
						EMachining_operation operationSlot = null;
						if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class)
						{
							operationSlot = this.createBottomAndSideRoughOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class)
						{
							operationSlot = this.createBottomAndSideFinishOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == FreeformOperation.class)
						{
							operationSlot = this.createFreeFormOperation(wsTmp);
						}
						operationsRanhura.addUnordered(operationSlot);
						if(this.alreadyUsed(ranhuraTmp) == false)
						{
							eSlot = this.createRoundedUProfileSlot(wsTmp, this.eWorkpiece, operationsRanhura);
							ranhuraTmp.setESlot(eSlot);
							this.usedFeatures.add(ranhuraTmp);
						} else
						{
							eSlot = ranhuraTmp.getESlot();
							AMachining_operation aMachining_operation = eSlot.createIts_operations(null);
							SdaiIterator iterator = operationsRanhura.createIterator();
							while(iterator.next())
							{
								EMachining_operation eMachining_operation = operationsRanhura.getCurrentMember(iterator);
								aMachining_operation.addUnordered(eMachining_operation);
							}
						}
						eMachining_workingstep = this.createElements(wsTmp.getId(), this.securityPlane, eSlot, operationSlot);
					}
					/**
					 *  ============= RANHURA PERFIL VEE ================
					 */
					else if (wsTmp.getFeature().getClass() == RanhuraPerfilVee.class)
					{
						ESlot eSlot;
						RanhuraPerfilVee ranhuraTmp = (RanhuraPerfilVee)wsTmp.getFeature();
						AMachining_operation operationsRanhura = null;
						if(this.alreadyUsed(ranhuraTmp) == false)
						{
							operationsRanhura = new AMachining_operation();
							ranhuraTmp.setOperations(operationsRanhura);
						} else
						{
							operationsRanhura = ranhuraTmp.getOperations();
						}
						EMachining_operation operationSlot = null;
						if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class)
						{
							operationSlot = this.createBottomAndSideRoughOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class)
						{
							operationSlot = this.createBottomAndSideFinishOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == FreeformOperation.class)
						{
							operationSlot = this.createFreeFormOperation(wsTmp);
						}
						operationsRanhura.addUnordered(operationSlot);
						if(this.alreadyUsed(ranhuraTmp) == false)
						{
							eSlot = this.createVeeProfileSlot(wsTmp, this.eWorkpiece, operationsRanhura);
							ranhuraTmp.setESlot(eSlot);
							this.usedFeatures.add(ranhuraTmp);
						} else
						{
							eSlot = ranhuraTmp.getESlot();
							AMachining_operation aMachining_operation = eSlot.createIts_operations(null);
							SdaiIterator iterator = operationsRanhura.createIterator();
							while(iterator.next())
							{
								EMachining_operation eMachining_operation = operationsRanhura.getCurrentMember(iterator);
								aMachining_operation.addUnordered(eMachining_operation);
							}
						}
						eMachining_workingstep = this.createElements(wsTmp.getId(), this.securityPlane, eSlot, operationSlot);
					}
					/**
					 *  ============= RANHURA PERFIL GENERICO ================
					 */
					else if (wsTmp.getFeature().getClass() == RanhuraPerfilBezier.class)
					{
						ESlot eSlot;
						RanhuraPerfilBezier ranhuraTmp = (RanhuraPerfilBezier)wsTmp.getFeature();
						AMachining_operation operationsRanhura = null;
						if(this.alreadyUsed(ranhuraTmp) == false)
						{
							operationsRanhura = new AMachining_operation();
							ranhuraTmp.setOperations(operationsRanhura);
						} else
						{
							operationsRanhura = ranhuraTmp.getOperations();
						}
						EMachining_operation operationSlot = null;
						if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class)
						{
							operationSlot = this.createBottomAndSideRoughOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class)
						{
							operationSlot = this.createBottomAndSideFinishOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == FreeformOperation.class)
						{
							operationSlot = this.createFreeFormOperation(wsTmp);
						}
						operationsRanhura.addUnordered(operationSlot);
						if(this.alreadyUsed(ranhuraTmp) == false)
						{
							eSlot = this.createBezierProfileSlot(wsTmp, this.eWorkpiece, operationsRanhura);
							ranhuraTmp.setESlot(eSlot);
							this.usedFeatures.add(ranhuraTmp);
						} else
						{
							eSlot = ranhuraTmp.getESlot();
							AMachining_operation aMachining_operation = eSlot.createIts_operations(null);
							SdaiIterator iterator = operationsRanhura.createIterator();
							while(iterator.next())
							{
								EMachining_operation eMachining_operation = operationsRanhura.getCurrentMember(iterator);
								aMachining_operation.addUnordered(eMachining_operation);
							}
						}
						eMachining_workingstep = this.createElements(wsTmp.getId(), this.securityPlane, eSlot, operationSlot);
					}
					/**
					 * =============  Degrau ====================
					 */
					else if (wsTmp.getFeature().getClass() == Degrau.class)
					{
						EStep eStep;
						Degrau degrauTmp = (Degrau)wsTmp.getFeature();
						AMachining_operation operationsDegrau = null;
						if(this.alreadyUsed(degrauTmp) == false)
						{
							operationsDegrau = new AMachining_operation();
							degrauTmp.setOperations(operationsDegrau);
						} else
						{
							operationsDegrau = degrauTmp.getOperations();
						}
						EMachining_operation operationStep = null;
						if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class)
						{
							operationStep = this.createBottomAndSideRoughOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class)
						{
							operationStep = this.createBottomAndSideFinishOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == FreeformOperation.class)
						{
							operationStep = this.createFreeFormOperation(wsTmp);
						}
						operationsDegrau.addUnordered(operationStep);
						
						if(this.alreadyUsed(degrauTmp) == false)
						{
							eStep = this.createStep(wsTmp, this.eWorkpiece, operationsDegrau);
							degrauTmp.setEStep(eStep);
							this.usedFeatures.add(degrauTmp);
						} else
						{
							eStep = degrauTmp.getEStep();
							AMachining_operation aMachining_operation = eStep.createIts_operations(null);
							SdaiIterator iterator = operationsDegrau.createIterator();
							while(iterator.next())
							{
								EMachining_operation eMachining_operation = operationsDegrau.getCurrentMember(iterator);
								aMachining_operation.addUnordered(eMachining_operation);
							}
						}
						eMachining_workingstep = this.createElements(wsTmp.getId(), this.securityPlane, eStep, operationStep);
					} 
					/**
					 *  =============== CAVIDADE ===============
					 */
					else if (wsTmp.getFeature().getClass() == Cavidade.class)
					{
							EClosed_pocket eClosed_pocket;
							Cavidade cavidadeTemp = (Cavidade)wsTmp.getFeature();
							AMachining_operation operationsCavidade = null;
							if(this.alreadyUsed(cavidadeTemp) == false)
							{
								operationsCavidade = new AMachining_operation();								
								cavidadeTemp.setOperations(operationsCavidade);
							} else
							{
								operationsCavidade = cavidadeTemp.getOperations();
							}
							EMachining_operation operationPocket = null;
							if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class)
							{
								operationPocket = this.createBottomAndSideRoughOperation(wsTmp);
							} else if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class)
							{
								operationPocket = this.createBottomAndSideFinishOperation(wsTmp);
							} else if(wsTmp.getOperation().getClass() == FreeformOperation.class)
							{
								operationPocket = this.createFreeFormOperation(wsTmp);
							}
							operationsCavidade.addUnordered(operationPocket);
							if(this.alreadyUsed(cavidadeTemp) == false)
							{
								eClosed_pocket = this.createClosedPocket(wsTmp, this.eWorkpiece, operationsCavidade);
								cavidadeTemp.seteClosed_pocket(eClosed_pocket);
								this.usedFeatures.add(cavidadeTemp);
							} else
							{
								eClosed_pocket = cavidadeTemp.geteClosed_pocket();
								AMachining_operation aMachining_operation = eClosed_pocket.createIts_operations(null);
								SdaiIterator iterator = operationsCavidade.createIterator();
								while(iterator.next())
								{
									EMachining_operation eMachining_operation = operationsCavidade.getCurrentMember(iterator);
									aMachining_operation.addUnordered(eMachining_operation);
								}
							}
							eMachining_workingstep = this.createElements(wsTmp.getId(), this.securityPlane, eClosed_pocket, operationPocket);
						} 
					/**
					 *  =================== BANHEIRA ================
					 */
					else if(wsTmp.getFeature().getClass() == CavidadeFundoArredondado.class)
					{
						EClosed_pocket eClosed_pocket;
						CavidadeFundoArredondado banheiraTmp = (CavidadeFundoArredondado)wsTmp.getFeature();
						AMachining_operation operationsBanheira = null;
						if(this.alreadyUsed(banheiraTmp) == false)
						{
							operationsBanheira = new AMachining_operation();
							banheiraTmp.setOperations(operationsBanheira);
						} else
						{
							operationsBanheira = banheiraTmp.getOperations();
						}
						EMachining_operation operationPocket = null;
						if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class)
						{
							operationPocket = this.createBottomAndSideRoughOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class)
						{
							operationPocket = this.createBottomAndSideFinishOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == FreeformOperation.class)
						{
							operationPocket = this.createFreeFormOperation(wsTmp);
						}
						operationsBanheira.addUnordered(operationPocket);
						
						if(this.alreadyUsed(banheiraTmp) == false)
						{
							eClosed_pocket = this.createRoundedBottomClosedPocket(wsTmp, this.eWorkpiece, operationsBanheira);
							banheiraTmp.seteClosed_pocket(eClosed_pocket);
							this.usedFeatures.add(banheiraTmp);
						}else
						{
							eClosed_pocket = banheiraTmp.geteClosed_pocket();
							AMachining_operation aMachining_operation = eClosed_pocket.createIts_operations(null);
							SdaiIterator iterator = operationsBanheira.createIterator();
							while(iterator.next())
							{
								EMachining_operation eMachining_operation = operationsBanheira.getCurrentMember(iterator);
								aMachining_operation.addUnordered(eMachining_operation);
							}
						}	
						eMachining_workingstep = this.createElements(wsTmp.getId(), this.securityPlane, eClosed_pocket, operationPocket);
					}
					/**
					 *  ================ REGION ===============
					 */
					else if(wsTmp.getFeature().getClass() == Region.class)
					{
						ERegion eRegion;
						Region region = (Region)wsTmp.getFeature();
						AMachining_operation operations = null;
						if(this.alreadyUsed(region) == false)
						{
							operations = new AMachining_operation();
							region.setOperations(operations);
						}else
						{
							operations = region.getOperations();
						}
						EMachining_operation operation = null;
						if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class)
						{
							operation = this.createBottomAndSideRoughOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == FreeformOperation.class)
						{
							operation = this.createFreeFormOperation(wsTmp);
						}
						operations.addUnordered(operation);
						
						if(this.alreadyUsed(region) == false)
						{
							eRegion = this.createRegion(wsTmp, eWorkpiece, operations);
							region.seteRegion(eRegion);
							this.usedFeatures.add(region);
						} else
						{
							eRegion = region.geteRegion();
							AMachining_operation aMachining_operation = eRegion.createIts_operations(null);
							SdaiIterator iterator = operations.createIterator();
							while(iterator.next())
							{
								EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
								aMachining_operation.addUnordered(eMachining_operation);
							}
						}
						eMachining_workingstep = this.createElements(wsTmp.getId(), this.securityPlane, eRegion, operation);
					}
					/**
					 *  ==================== GENERAL CLOSED POCKET ===================
					 */
					else if(wsTmp.getFeature().getClass() == GeneralClosedPocket.class)
					{
						EClosed_pocket eClosed_pocket;
						GeneralClosedPocket general = (GeneralClosedPocket)wsTmp.getFeature();
						AMachining_operation operations = null;
						if(this.alreadyUsed(general) == false)
						{
							operations = new AMachining_operation();
							general.setOperations(operations);
						} else
						{
							operations = general.getOperations();
						}
						EMachining_operation operation = null;
						if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class)
						{
							operation = this.createBottomAndSideRoughOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class)
						{
							operation = this.createBottomAndSideFinishOperation(wsTmp);
						} else if(wsTmp.getOperation().getClass() == FreeformOperation.class)
						{
							operation = this.createFreeFormOperation(wsTmp);
						}
						operations.addUnordered(operation);
						if(this.alreadyUsed(general) == false)
						{
							eClosed_pocket = this.createGeneralClosedPocket(wsTmp, eWorkpiece, operations);
							general.seteClosed_pocket(eClosed_pocket);
							this.usedFeatures.add(general);
						} else
						{
							eClosed_pocket = general.geteClosed_pocket();
							AMachining_operation aMachining_operation = eClosed_pocket.createIts_operations(null);
							SdaiIterator iterator = operations.createIterator();
							while(iterator.next())
							{
								EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
								aMachining_operation.addUnordered(eMachining_operation);
							}
						}
						eMachining_workingstep = this.createElements(wsTmp.getId(), this.securityPlane, eClosed_pocket, operation);
					}
					
					aExecutable.addByIndex(i + 1, eMachining_workingstep);
			}
			if (workinstepsFaceTmp.size() != 0)
			{
				
				EWorkplan eWorkplanFace = this.createWorkplan("workplan setup " + Face.getStringFace(index) , aExecutable, setup);
				aWorkplan.addByIndex(contador, eWorkplanFace);
				contador++;
			}
		}
		
		// ---- CREATING WORKPLAN ----
		this.eWorkplan = this.createWorkplan("Main workplan", aWorkplan);
		this.eProject.setMain_workplan(null, this.eWorkplan);
		
		// --- GENERATING THE PHYSICAL FILE ---
		//this.generateFile21();
		
		//  ready !
	}
	
	

	



	protected Boolean alreadyUsed(Feature feature)
	{
		boolean answer = false;
		if (this.usedFeatures.size() > 0)
		{
			for(int i = 0; i < this.usedFeatures.size(); i++)
			{
				if (feature.equals(this.usedFeatures.get(i)))
				{
					answer = true;
					break;
				}
			}
		}
		return answer;
	}
	private void setProject(String id, double globalTolerance) throws SdaiException
	{
		eProject = (EProject)this.model.createEntityInstance(EProject.class);
		eProject.setIts_id(null, id);
//		EPerson_and_address ePerson_and_address = (EPerson_and_address)this.model.createEntityInstance(EPerson_and_address.class);
//		EPerson ePerson = (EPerson)model.createEntityInstance(EPerson.class);
//		ePerson.setFirst_name(null, this.owner);
//		ePerson.createMiddle_names(null);
//		ePerson.createPrefix_titles(null);
//		ePerson.createSuffix_titles(null);
//		ePerson.setId(null, "" + this.projeto.getDadosDeProjeto().getUserID());
//		ePerson_and_address.setIts_person(null, ePerson);
//		eProject.setIts_owner(null, ePerson_and_address);
		AWorkpiece aWorkpiece = eProject.createIts_workpieces(null);
		aWorkpiece.addUnordered(this.createWorkpiece("workpiece", globalTolerance, workpieceLength, workpiecewidth, workpieceHight, clampingPoints));
	}
	private EWorkplan createWorkplan(String id, AExecutable machiningWorkingsteps, ESetup setup) throws SdaiException
	{
		EWorkplan eWorkplan = (EWorkplan)this.model.createEntityInstance(EWorkplan.class);
		eWorkplan.setIts_id(null, id);
		eWorkplan.setIts_setup(null, setup);
		AExecutable elements = eWorkplan.createIts_elements(null);

		SdaiIterator iterator = machiningWorkingsteps.createIterator();
		int i = 1;
		while(iterator.next())
		{
			EExecutable executable = machiningWorkingsteps.getCurrentMember(iterator);
			elements.addByIndex(i, executable);
			i++;
		}
		return eWorkplan;
	}
	private EWorkplan createWorkplan(String id, AWorkplan workplans) throws SdaiException
	{
		eWorkplan = (EWorkplan)this.model.createEntityInstance(EWorkplan.class);
		eWorkplan.setIts_id(null, id);
//		eWorkplan.setIts_setup(null, setup);
		AExecutable elements = eWorkplan.createIts_elements(null);

		SdaiIterator iterator = workplans.createIterator();
		int i = 1;
		while(iterator.next())
		{
			EExecutable executable = workplans.getCurrentMember(iterator);
			elements.addByIndex(i, executable);
			i++;
		}
		return eWorkplan;
	}
	private EWorkpiece createWorkpiece(String id, double globalTolerance, double length, double width, double hight, ArrayList<Point3d> clampingPoints) throws SdaiException
	{
		if (this.eWorkpiece == null)
		{
			this.eWorkpiece = (EWorkpiece)this.model.createEntityInstance(EWorkpiece.class);
			ACartesian_point clamping = this.eWorkpiece.createClamping_positions(null);
			if (clampingPoints != null)
			{
				int i = 1;
				for(Point3d point : clampingPoints)
				{
					ECartesian_point eCartesian_point = createCartesianPoint("clamping point " + i, point);
					clamping.addUnordered(eCartesian_point);
					i++;
				}
				
			}
			ArrayList<Double> workpieceLocation = new ArrayList<Double>();
			ArrayList<Double> workpieceAxis = new ArrayList<Double>();
			ArrayList<Double> workpieceRefDirection = new ArrayList<Double>();
			workpieceLocation.add(0, 0.0);
			workpieceLocation.add(1, 0.0);
			workpieceLocation.add(2, 0.0);
			
			workpieceAxis.add(0, 0.0);
			workpieceAxis.add(1, 0.0);
			workpieceAxis.add(2, 1.0);
			
			workpieceRefDirection.add(0, 1.0);
			workpieceRefDirection.add(1, 0.0);
			workpieceRefDirection.add(2, 0.0);
			this.workpiecePlacement = createAxis2Placement3D("workpiece placement", workpieceLocation, workpieceAxis, workpieceRefDirection);

			
			eWorkpiece.setIts_id(null, id);
//			eWorkpiece.setIts_material(null, createMaterial(this.projeto.getDadosDeProjeto().getMaterial().getName(), this.projeto.getBloco().getMaterial().getTypeMaterial(), this.projeto.getDadosDeProjeto().getMaterial().getProperties().get(0).getParameterName(), this.projeto.getDadosDeProjeto().getMaterial().getProperties().get(0).getParameterValue(), this.projeto.getDadosDeProjeto().getMaterial().getProperties().get(0).getParameterUnit()));
			eWorkpiece.setIts_material(null, createMaterial(this.projeto.getBloco().getMaterial().getName(), this.projeto.getBloco().getMaterial().getTypeMaterial(), this.projeto.getBloco().getMaterial().getProperties().get(0).getParameterName(), this.projeto.getBloco().getMaterial().getProperties().get(0).getParameterValue(), this.projeto.getBloco().getMaterial().getProperties().get(0).getParameterUnit()));
			eWorkpiece.setGlobal_tolerance(null, globalTolerance);
			eWorkpiece.setIts_bounding_geometry(null, createBoundingGoemetry("piece", this.workpiecePlacement, length, width, hight));
			
			
		}
		
		return eWorkpiece;
	}
	private EBlock createBoundingGoemetry(String name, EAxis2_placement_3d position, double length, double width, double hight) throws SdaiException
	{
		EBlock eBlock = (EBlock)model.createEntityInstance(EBlock.class);
		eBlock.setName(null, name);
		eBlock.setPosition(null, position);
		eBlock.setX(null, length);
		eBlock.setY(null, width);
		eBlock.setZ(null, hight);
		return eBlock;
	}
	private EMachining_workingstep createElements(String id, EPlane secPlane, EManufacturing_feature itsFeature, EMachining_operation machining_operation) throws SdaiException
	{
		EMachining_workingstep eMachining_workingstep = (EMachining_workingstep)model.createEntityInstance(EMachining_workingstep.class);
		eMachining_workingstep.setIts_id(null, id);
		eMachining_workingstep.setIts_secplane(null, secPlane);
		eMachining_workingstep.setIts_feature(null, itsFeature);
		eMachining_workingstep.setIts_operation(null, machining_operation);
		
		
		return eMachining_workingstep;
	}
	private ESetup createSetup(String id, ArrayList<ArrayList<Double>> origin) throws SdaiException
	{
		ESetup eSetup = (ESetup)model.createEntityInstance(ESetup.class);
		eSetup.setIts_id(null, id);
		
		
		ArrayList<ArrayList<Double>> positionSecPlane = new ArrayList<ArrayList<Double>>();
		
		ArrayList<Double> location = new ArrayList<Double>();
		ArrayList<Double> axis = new ArrayList<Double>();
		ArrayList<Double> refDirection = new ArrayList<Double>();
		location.add(0, 0.0);
		location.add(1, 0.0);
		location.add(2, workpieceHight + distanceUpperSurfaceWorkpiece);
		
		axis.add(0, 0.0);
		axis.add(1, 0.0);
		axis.add(2, 1.0);

		refDirection.add(0, 1.0);
		refDirection.add(1, 0.0);
		refDirection.add(2, 0.0);

		positionSecPlane.add(0, location);
		positionSecPlane.add(1, axis);
		positionSecPlane.add(2, refDirection);
		
		// == coordinates for WORKPIECE_SETUP
		ArrayList<Double> locationWPS = new ArrayList<Double>();

		locationWPS.add(0, 0.0);
		locationWPS.add(1, 0.0);
		locationWPS.add(2, 0.0);
		//===
		
		securityPlane = createPlane("security plane", createAxis2Placement3D("", positionSecPlane.get(0), positionSecPlane.get(1), positionSecPlane.get(2)));
		eSetup.setIts_origin(null, createAxis2Placement3D("origin", origin.get(0), origin.get(1), origin.get(2)));
		eSetup.setIts_secplane(null, securityPlane);
		AWorkpiece_setup aWorkpiece_setup = eSetup.createIts_workpiece_setup(null);
		EWorkpiece_setup eWorkpiece_setup = (EWorkpiece_setup)model.createEntityInstance(EWorkpiece_setup.class);
		eWorkpiece_setup.setIts_workpiece(null, eWorkpiece);
		eWorkpiece_setup.setIts_origin(null, createAxis2Placement3D("", locationWPS, axis, refDirection));
		eWorkpiece_setup.createIts_instructions(null);
		aWorkpiece_setup.addUnordered(eWorkpiece_setup);
		
		return eSetup;
	}
	private EMaterial createMaterial(String standarId, String materialId, String paramName, double paramValue, String units) throws SdaiException
	{
		EMaterial eMaterial = (EMaterial)this.model.createEntityInstance(EMaterial.class);
		eMaterial.setStandard_identifier(null, standarId);
		eMaterial.setMaterial_identifier(null, materialId);
		ENumeric_parameter eNumeric_parameter = (ENumeric_parameter)this.model.createEntityInstance(ENumeric_parameter.class);
		eNumeric_parameter.setParameter_name(null, paramName);
		eNumeric_parameter.setIts_parameter_value(null, paramValue);
		eNumeric_parameter.setIts_parameter_unit(null, units);
		AProperty_parameter aProperty_parameter = eMaterial.createMaterial_property(null);
		aProperty_parameter.addUnordered(eNumeric_parameter);
		return eMaterial;
	}
	private EMaterial createMaterial(Material material) throws SdaiException
	{
		EMaterial eMaterial = (EMaterial)this.model.createEntityInstance(EMaterial.class);
		eMaterial.setStandard_identifier(null, material.getName());
		eMaterial.setMaterial_identifier(null, material.getMaterialIdentifier());
		eMaterial.createMaterial_property(null);
		return eMaterial;
	}
	private ECartesian_point createCartesianPoint(String name, ArrayList<Double> coordinates) throws SdaiException
	{
		ECartesian_point eCartesian_point = null;
		if(coordinates.size() == 3)
		{
			eCartesian_point = (ECartesian_point)model.createEntityInstance(ECartesian_point.class);
			eCartesian_point.setName(null, name);
			A_double coord = eCartesian_point.createCoordinates(null);
			coord.addByIndex(1, coordinates.get(0));
			coord.addByIndex(2, coordinates.get(1));
			coord.addByIndex(3, coordinates.get(2));
		}
		return eCartesian_point;
	}
	private ECartesian_point createCartesianPoint(String name, Point3d coordinates) throws SdaiException
	{
		ECartesian_point eCartesian_point = (ECartesian_point)model.createEntityInstance(ECartesian_point.class);
		eCartesian_point.setName(null, name);
		A_double coord = eCartesian_point.createCoordinates(null);
		coord.addByIndex(1, coordinates.x);
		coord.addByIndex(2, coordinates.y);
		coord.addByIndex(3, coordinates.z);
		return eCartesian_point;
	}
	private EDirection createDirection(String name, ArrayList<Double> ratios) throws SdaiException
	{
		EDirection eDirection = null;
		if(ratios.size() == 3)
		{
			eDirection = (EDirection)model.createEntityInstance(EDirection.class);
			if (name != null)
			{
				eDirection.setName(null, name);				
			}
			A_double rat = eDirection.createDirection_ratios(null);
			rat.addByIndex(1, ratios.get(0));
			rat.addByIndex(2, ratios.get(1));
			rat.addByIndex(3, ratios.get(2));
		}
		return eDirection;
	}
	private EDirection createDirection(String name, Point3d ratios) throws SdaiException
	{
		EDirection eDirection = null;

		eDirection = (EDirection) model.createEntityInstance(EDirection.class);
		if (name != null)
		{
			eDirection.setName(null, name);
		}
		A_double rat = eDirection.createDirection_ratios(null);
		rat.addByIndex(1, ratios.x);
		rat.addByIndex(2, ratios.y);
		rat.addByIndex(3, ratios.z);
	
		return eDirection;
	}
	private EAxis2_placement_3d createAxis2Placement3D(String name, Point3d location, ArrayList<Double> axis, ArrayList<Double> refDirection) throws SdaiException
	{
		EAxis2_placement_3d eAxis2_placement_3d = (EAxis2_placement_3d)this.model.createEntityInstance(EAxis2_placement_3d.class);
		if(name != null)
		{
			eAxis2_placement_3d.setName(null, name);			
		}
		eAxis2_placement_3d.setLocation(null, this.createCartesianPoint("", location));
		eAxis2_placement_3d.setAxis(null, this.createDirection("", axis));
		eAxis2_placement_3d.setRef_direction(null, this.createDirection("", refDirection));
		return eAxis2_placement_3d;
	}
	private EAxis2_placement_3d createAxis2Placement3D(String name, ArrayList<Double> location, ArrayList<Double> axis, ArrayList<Double> refDirection) throws SdaiException
	{
		EAxis2_placement_3d eAxis2_placement_3d = (EAxis2_placement_3d)model.createEntityInstance(EAxis2_placement_3d.class);
		if(name != null)
		{
			eAxis2_placement_3d.setName(null, name);			
		}
		eAxis2_placement_3d.setLocation(null, createCartesianPoint("", location));
		eAxis2_placement_3d.setAxis(null, createDirection("", axis));
		eAxis2_placement_3d.setRef_direction(null, createDirection("", refDirection));
		return eAxis2_placement_3d;
	}
	private EPlane createPlane(String name, EAxis2_placement_3d position) throws SdaiException
	{
		EPlane ePlane = (EPlane)model.createEntityInstance(EPlane.class);
		ePlane.setName(null, name);
		ePlane.setPosition(null, position);
		return ePlane;
	}
	private EBoss createBoss(Boss boss, EWorkpiece eWorkpiece) throws SdaiException 
	{
		EBoss eBoss = (EBoss) this.model.createEntityInstance(EBoss.class);
		eBoss.setIts_id(null, boss.getNome());
		eBoss.setIts_workpiece(null, eWorkpiece);
		
		if(boss.getClass() == CircularBoss.class)
		{
			CircularBoss circular = (CircularBoss)boss;
			double slope = Math.atan2(((circular.getDiametro2() - circular.getDiametro1()) / 2), circular.getAltura());
			eBoss.setSlope(null, slope);
			ECircular_closed_profile eProfile = (ECircular_closed_profile)this.model.createEntityInstance(ECircular_closed_profile.class);
			eProfile.setDiameter(null, this.createTolerancedLengthMeasure(circular.getDiametro1()));
			
			eProfile.setPlacement(null, this.createAxis2Placement3D(boss.getNome() + " - placement", circular.getPosition().getCoordinates(), circular.getPosition().getAxis(), circular.getPosition().getRefDirection()));
		}else if(boss.getClass() == RectangularBoss.class)
		{
			RectangularBoss rectangular = (RectangularBoss)boss;
			
			ERectangular_closed_profile eRectangular_closed_profile = (ERectangular_closed_profile)this.model.createEntityInstance(ERectangular_closed_profile.class);
			eRectangular_closed_profile.setProfile_length(null, this.createTolerancedLengthMeasure(rectangular.getL1()));
			eRectangular_closed_profile.setProfile_length(null, this.createTolerancedLengthMeasure(rectangular.getL2()));
			eRectangular_closed_profile.setPlacement(null, this.createAxis2Placement3D(rectangular.getPosition().getName(), rectangular.getPosition().getCoordinates(), rectangular.getPosition().getAxis(), rectangular.getPosition().getRefDirection()));
		}else if(boss.getClass() == GeneralProfileBoss.class)
		{
			GeneralProfileBoss general = (GeneralProfileBoss)boss;
			
			EGeneral_closed_profile eGeneral_closed_profile = (EGeneral_closed_profile)this.model.createEntityInstance(EGeneral_closed_profile.class);
			eGeneral_closed_profile.setPlacement(null, this.createAxis2Placement3D(general.getPosition().getName(), general.getPosition().getCoordinates(), general.getPosition().getAxis(), general.getPosition().getRefDirection()));
			
		}
		
		return eBoss;
	}
	private EClosed_pocket createClosedPocket(String id, EWorkpiece workpiece, AMachining_operation operations, EAxis2_placement_3d placement, double depth, int bottomCondition, double planarRadius, double ortogonalRadius, EClosed_profile profile) throws SdaiException
	{
		
		EClosed_pocket eClosed_pocket = (EClosed_pocket)model.createEntityInstance(EClosed_pocket.class);
		
		eClosed_pocket.setIts_id(null, id);
		eClosed_pocket.setIts_workpiece(null, eWorkpiece);
		AMachining_operation aMachining_operation = eClosed_pocket.createIts_operations(null);
		
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		
		
		eClosed_pocket.setFeature_placement(null, placement);
		
		ArrayList<Double> depthPlacement = new ArrayList<Double>();
		depthPlacement.add(0.0);
		depthPlacement.add(0.0);
		depthPlacement.add(-depth);
		
		eClosed_pocket.setIts_workpiece(null, eWorkpiece);
		eClosed_pocket.setDepth(null, createPlane("depth of closed pocket", createAxis2Placement3D("", depthPlacement, featureAxis, featureRefDirection)));
		eClosed_pocket.setSlope(null, 0.0);
		if (bottomCondition == 0) // planar
		{
			EPlanar_pocket_bottom_condition	planar_pocket_bottom_condition = (EPlanar_pocket_bottom_condition)model.createEntityInstance(EPlanar_pocket_bottom_condition.class);
			eClosed_pocket.setBottom_condition(null, planar_pocket_bottom_condition);
		}
		
		eClosed_pocket.setPlanar_radius(null, createTolerancedLengthMeasure(planarRadius, globalTolerance));
		eClosed_pocket.setOrthogonal_radius(null, createTolerancedLengthMeasure(ortogonalRadius, globalTolerance));
		eClosed_pocket.setFeature_boundary(null, profile);
		return eClosed_pocket;
	}
	private ERegion_surface_list createRegion(Workingstep wsTmp, EWorkpiece workpiece, AMachining_operation operations) throws SdaiException 
	{
		Region reg = (Region)wsTmp.getFeature();
		Axis2Placement3D position = reg.getPosition();
		ERegion_surface_list eRegion = (ERegion_surface_list)this.model.createEntityInstance(ERegion_surface_list.class);
		eRegion.setIts_id(null, reg.getNome());
		eRegion.setIts_workpiece(null, workpiece);
		EAxis2_placement_3d placement = this.createAxis2Placement3D(reg.getNome() + " placement", position.getCoordinates(), position.getAxis(), position.getRefDirection());
		eRegion.setFeature_placement(null, placement);
		
		AMachining_operation aMachining_operation = eRegion.createIts_operations(null);
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		ABounded_surface lista = eRegion.createSurface_list(null);
		
		EBezier_surface bezier = this.createBezierSurface(wsTmp);
		lista.addByIndex(1, bezier);
		
		return eRegion;
	}
	private EBezier_surface createBezierSurface(Workingstep ws) throws SdaiException
	{
		Region region = (Region)ws.getFeature();
		EBezier_surface eBezier_surface = (EBezier_surface)this.model.createEntityInstance(EBezier_surface.class);
		
		eBezier_surface.setName(null, "surface - " + ws.getFeature().getNome());
		eBezier_surface.setU_degree(null, region.getControlVertex().length);
		eBezier_surface.setV_degree(null, region.getControlVertex()[0].length);
		Point3d[][] vertex = region.getControlVertex(); // control points of surface
		AaCartesian_point controlVertex = eBezier_surface.createControl_points_list(null); // creating a array of arrays of cartesian_point
		
		for(int i = 0; i < vertex.length; i++)
		{			
			ACartesian_point aTmp = controlVertex.addAggregateByIndex(i + 1); // creating a array of cartesian_point into position i + 1 of controlVertex Array
			for(int j = 0; j < vertex[i].length; j++)
			{
				ECartesian_point point = this.createCartesianPoint("control vertex ", vertex[i][j]); // creating the cartesian_point Entity
				aTmp.addByIndex(j + 1, point); //adding the cartesian_point into Array aTmp
			}
//			controlVertex.addAggregateByIndex(i + 1, aTmp); // is necessary?
		}
		
		eBezier_surface.setSurface_form(null, EB_spline_surface_form.UNSPECIFIED);
		eBezier_surface.setU_closed(null, ELogical.FALSE);
		eBezier_surface.setV_closed(null, ELogical.FALSE);
		
		return eBezier_surface;
	}
	private EClosed_pocket createGeneralClosedPocket(Workingstep wsTmp, EWorkpiece eWorkpiece, AMachining_operation operations) throws SdaiException 
	{
		GeneralClosedPocket general = (GeneralClosedPocket)wsTmp.getFeature();
		Axis2Placement3D position = general.getPosition(); /** =========== cuidado =========*/
		EClosed_pocket eClosed_pocket = (EClosed_pocket)this.model.createEntityInstance(EClosed_pocket.class);
		eClosed_pocket.setIts_id(null, general.getNome());
		eClosed_pocket.setIts_workpiece(null, eWorkpiece);
//		System.out.println("GENERATOR =  " + position.getName());
		EAxis2_placement_3d placement = this.createAxis2Placement3D(position.getName(), position.getCoordinates(), position.getAxis(), position.getRefDirection());
		placement.setName(null, position.getName());
		eClosed_pocket.setFeature_placement(null, placement);
		
		AMachining_operation aMachining_operation = eClosed_pocket.createIts_operations(null);
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		Point3d pocketDepthLocation = new Point3d(0, 0, -general.getProfundidade());
		eClosed_pocket.setDepth(null, this.createPlane(general.getNome() + " depth location", this.createAxis2Placement3D(general.getNome() + " depth", pocketDepthLocation, position.getAxis(), position.getRefDirection())));
		EPocket_bottom_condition bottom_condition;
		if(general.isPassante() == true)
		{
			bottom_condition = (EThrough_pocket_bottom_condition)this.model.createEntityInstance(EThrough_pocket_bottom_condition.class);
		} else
		{
			bottom_condition = (EPlanar_pocket_bottom_condition)this.model.createEntityInstance(EPlanar_pocket_bottom_condition.class);
		}
		eClosed_pocket.setBottom_condition(null, bottom_condition);
		eClosed_pocket.setOrthogonal_radius(null, this.createTolerancedLengthMeasure(general.getRadius()));
		EGeneral_closed_profile profile = (EGeneral_closed_profile)this.model.createEntityInstance(EGeneral_closed_profile.class);
		eClosed_pocket.setFeature_boundary(null, profile);
		EPolyline ePolyline = (EPolyline)this.model.createEntityInstance(EPolyline.class);
		ePolyline.setName(null, "contour of pocket");
		ACartesian_point aCartesian_point = ePolyline.createPoints(null);
		Point3d coordinates = null;
		for(int i = 0; i < general.getPoints().size(); i++)
		{
			coordinates = new Point3d(general.getPoints().get(i).getX() - general.X, general.getPoints().get(i).getY() - general.Y, 0);
			ECartesian_point eCartesian_point = this.createCartesianPoint("P " + i + 1, coordinates);
			aCartesian_point.addByIndex(i + 1, eCartesian_point);
//			System.out.println("Point2D " + "ponto" + i + "new Point2D.Double(" + general.getPoints().get(i).getX() + ", " + general.getPoints().get(i).getY() + ")");
//			System.out.println("points.add(" + "new Point2D.Double(" + general.getPoints().get(i).getX() + ", " + general.getPoints().get(i).getY() + "));");
		}
		profile.setPlacement(null, this.createAxis2Placement3D("profile placement", new Point3d(), position.getAxis(), position.getRefDirection()));
		profile.setClosed_profile_shape(null, ePolyline);
		
		// ==== BOSS IS RELATED TO CLOSED POCKET ====
				ABoss aBoss = eClosed_pocket.createIts_boss(null);
				for(int i = 0; i < general.getItsBoss().size(); i++)
				{
					Boss bossTmp = general.getItsBoss().get(i);
					EBoss eBoss = (EBoss)this.model.createEntityInstance(EBoss.class);
					eBoss.setIts_id(null, bossTmp.getNome());
					eBoss.setIts_workpiece(null, eWorkpiece);
					eBoss.setFeature_placement(null, createAxis2Placement3D(bossTmp.getNome() + " placement", new Point3d(bossTmp.X, bossTmp.Y, bossTmp.Z), bossTmp.getPosition().getAxis(), bossTmp.getPosition().getRefDirection()));
					Point3d bossDepthLocation = new Point3d(0, 0, bossTmp.getAltura());
					eBoss.setDepth(null, this.createPlane(bossTmp.getNome() + " heigh location", this.createAxis2Placement3D(bossTmp.getNome() + "heigh", bossDepthLocation, bossTmp.getPosition().getAxis(), bossTmp.getPosition().getRefDirection())));
					if (bossTmp.getClass() == CircularBoss.class)
					{
						CircularBoss circular = (CircularBoss)bossTmp;
						double slope = Math.atan2(((circular.getDiametro2() - circular.getDiametro1()) / 2), circular.getAltura());
						ECircular_closed_profile profileBoss = (ECircular_closed_profile)this.model.createEntityInstance(ECircular_closed_profile.class);
						profileBoss.setPlacement(null, this.createAxis2Placement3D("profile location", new Point3d(0, 0, 0), bossTmp.getPosition().getAxis(), bossTmp.getPosition().getRefDirection()));
						profileBoss.setDiameter(null, this.createTolerancedLengthMeasure(circular.getDiametro1()));
						eBoss.setIts_boundary(null, profileBoss);
						eBoss.setSlope(null, slope);
					}else if (bossTmp.getClass() == RectangularBoss.class)
					{
						RectangularBoss rectangular = (RectangularBoss)bossTmp;
						ERectangular_closed_profile profileBoss = (ERectangular_closed_profile)this.model.createEntityInstance(ERectangular_closed_profile.class);
						profileBoss.setPlacement(null, this.createAxis2Placement3D("profile location", new Point3d(0, 0, 0), bossTmp.getPosition().getAxis(), bossTmp.getPosition().getRefDirection()));
						profileBoss.setProfile_length(null, this.createTolerancedLengthMeasure(rectangular.getL1()));
						profileBoss.setProfile_width(null, this.createTolerancedLengthMeasure(rectangular.getL2()));
						eBoss.setIts_boundary(null, profileBoss);
						eBoss.setFeature_placement(null, createAxis2Placement3D(bossTmp.getNome() + " placement", new Point3d(bossTmp.X + ((RectangularBoss)bossTmp).getL1() / 2, bossTmp.Y + ((RectangularBoss)bossTmp).getL1() / 2, bossTmp.Z), bossTmp.getPosition().getAxis(), bossTmp.getPosition().getRefDirection()));

					}else if (bossTmp.getClass() == GeneralProfileBoss.class)
					{
						GeneralProfileBoss boss = (GeneralProfileBoss)bossTmp;
						eBoss.setIts_boundary(null, this.createProfile(boss));
					}
					aBoss.addUnordered(eBoss);
				}
		
		return eClosed_pocket;
	}
	
	private EGeneral_closed_profile createProfile(GeneralProfileBoss boss) throws SdaiException 
	{
		ArrayList<Path> paths = boss.getPaths();
		EGeneral_closed_profile eGeneral_closed_profile = (EGeneral_closed_profile)this.model.createEntityInstance(EGeneral_closed_profile.class);
		
		EComposite_curve eComposite_curve = (EComposite_curve)this.model.createEntityInstance(EComposite_curve.class);
		eComposite_curve.setName(null, boss.getNome() + " - profile contourn");
		AComposite_curve_segment aComposite_curve_segment = eComposite_curve.createSegments(null);
		for(int i = 0; i< paths.size(); i++)
		{
			Path pathTmp = paths.get(i);
			EComposite_curve_segment eComposite_curve_segment = (EComposite_curve_segment)this.model.createEntityInstance(EComposite_curve_segment.class);
			if(pathTmp.getClass() == CircularPath.class)
			{
				eComposite_curve_segment.setTransition(null, ETransition_code.CONT_SAME_GRADIENT);
				ECircle eCircle = (ECircle)this.model.createEntityInstance(ECircle.class);
				EAxis2_placement_3d placement = this.createAxis2Placement3D("", pathTmp.getInitialPoint(), boss.getPosition().getAxis(), boss.getPosition().getRefDirection());
				eCircle.setPosition(null, placement);
				eCircle.setRadius(null, ((CircularPath)pathTmp).getRadius());
				eComposite_curve_segment.setParent_curve(null, eCircle);
			}else if(pathTmp.getClass() == LinearPath.class)
			{
				eComposite_curve_segment.setTransition(null, ETransition_code.CONTINUOUS);
				eComposite_curve_segment.setSame_sense(null, Boolean.TRUE);
				EPolyline ePolyline = (EPolyline)this.model.createEntityInstance(EPolyline.class);
				ACartesian_point points = ePolyline.createPoints(null);
				ECartesian_point initialPoint = this.createCartesianPoint("", pathTmp.getInitialPoint());
				ECartesian_point finalPoint = this.createCartesianPoint("", pathTmp.getFinalPoint());
				points.addByIndex(1, initialPoint);
				points.addByIndex(2, finalPoint);
				
				eComposite_curve_segment.setParent_curve(null, ePolyline);
			}
			aComposite_curve_segment.addByIndex(i + 1, eComposite_curve_segment);
		}
		eGeneral_closed_profile.setPlacement(null, this.createAxis2Placement3D("profile placement ", boss.getPosition().getCoordinates(), boss.getPosition().getAxis(), boss.getPosition().getRefDirection()));
		eGeneral_closed_profile.setClosed_profile_shape(null, eComposite_curve);
		return eGeneral_closed_profile;
	}


	private EClosed_pocket createClosedPocket(Workingstep ws, EWorkpiece eWorkpiece, AMachining_operation operations) throws SdaiException
	{
		Cavidade cavidade = (Cavidade)ws.getFeature();
		Axis2Placement3D position = cavidade.getPosition();
		EClosed_pocket eClosed_pocket = (EClosed_pocket)this.model.createEntityInstance(EClosed_pocket.class);
		eClosed_pocket.setIts_id(null, cavidade.getNome());
		eClosed_pocket.setIts_workpiece(null, eWorkpiece);
		
		EAxis2_placement_3d placement = this.createAxis2Placement3D(cavidade.getNome() + " placement", position.getCoordinates(), position.getAxis(), position.getRefDirection());
		eClosed_pocket.setFeature_placement(null, placement);
		
		AMachining_operation aMachining_operation = eClosed_pocket.createIts_operations(null);
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		Point3d pocketDepthLocation = new Point3d(0, 0, -cavidade.getProfundidade());
		eClosed_pocket.setDepth(null, this.createPlane(cavidade.getNome() + " depth location", this.createAxis2Placement3D(cavidade.getNome() + " depth", pocketDepthLocation, position.getAxis(), position.getRefDirection())));
		EPocket_bottom_condition bottom_condition;
		if(cavidade.isPassante() == true)
		{
			bottom_condition = (EThrough_pocket_bottom_condition)this.model.createEntityInstance(EThrough_pocket_bottom_condition.class);
		} else
		{
			bottom_condition = (EPlanar_pocket_bottom_condition)this.model.createEntityInstance(EPlanar_pocket_bottom_condition.class);
		}
		eClosed_pocket.setBottom_condition(null, bottom_condition);
		eClosed_pocket.setOrthogonal_radius(null, this.createTolerancedLengthMeasure(cavidade.getRaio(), cavidade.getTolerancia()));
		eClosed_pocket.setFeature_boundary(null, this.createRectangularClosedProfile(cavidade));
		// ==== BOSS IS RELATED TO CLOSED POCKET ====
		ABoss aBoss = eClosed_pocket.createIts_boss(null);
		for(int i = 0; i < cavidade.getItsBoss().size(); i++)
		{
			Boss bossTmp = cavidade.getItsBoss().get(i);
			EBoss eBoss = (EBoss)this.model.createEntityInstance(EBoss.class);
			eBoss.setIts_id(null, bossTmp.getNome());
			eBoss.setIts_workpiece(null, eWorkpiece);
//			System.out.println("POSICAO AXIS 2 PLACEMENT 3D" + bossTmp.getNome());
			eBoss.setFeature_placement(null, createAxis2Placement3D(bossTmp.getNome() + " placement", bossTmp.getPosition().getCoordinates(), bossTmp.getPosition().getAxis(), bossTmp.getPosition().getRefDirection()));
			Point3d bossDepthLocation = new Point3d(0, 0, bossTmp.getAltura());
			eBoss.setDepth(null, this.createPlane(bossTmp.getNome() + " heigh location", this.createAxis2Placement3D(bossTmp.getNome() + "heigh", bossDepthLocation, bossTmp.getPosition().getAxis(), bossTmp.getPosition().getRefDirection())));
			if (bossTmp.getClass() == CircularBoss.class)
			{
				CircularBoss circular = (CircularBoss)bossTmp;
				double slope = Math.atan2(((circular.getDiametro2() - circular.getDiametro1()) / 2), circular.getAltura());
				ECircular_closed_profile profile = (ECircular_closed_profile)this.model.createEntityInstance(ECircular_closed_profile.class);
				profile.setPlacement(null, this.createAxis2Placement3D("profile location", new Point3d(0, 0, 0), bossTmp.getPosition().getAxis(), bossTmp.getPosition().getRefDirection()));
				profile.setDiameter(null, this.createTolerancedLengthMeasure(circular.getDiametro1()));
				eBoss.setIts_boundary(null, profile);
				eBoss.setSlope(null, slope);
			}else if (bossTmp.getClass() == RectangularBoss.class)
			{
				RectangularBoss rectangular = (RectangularBoss)bossTmp;
				ERectangular_closed_profile profile = (ERectangular_closed_profile)this.model.createEntityInstance(ERectangular_closed_profile.class);
				profile.setPlacement(null, this.createAxis2Placement3D("profile location", new Point3d(0, 0, 0), bossTmp.getPosition().getAxis(), bossTmp.getPosition().getRefDirection()));
				profile.setProfile_length(null, this.createTolerancedLengthMeasure(rectangular.getL1()));
				profile.setProfile_width(null, this.createTolerancedLengthMeasure(rectangular.getL2()));
				eBoss.setIts_boundary(null, profile);
			}else if (bossTmp.getClass() == GeneralProfileBoss.class)
			{
				GeneralProfileBoss boss = (GeneralProfileBoss)bossTmp;
				eBoss.setIts_boundary(null, this.createProfile(boss));
			}
			aBoss.addUnordered(eBoss);
		}
		
		
		return eClosed_pocket;
	}
	private EClosed_pocket createRoundedBottomClosedPocket(Workingstep ws, EWorkpiece eWorkpiece, AMachining_operation operations) throws SdaiException
	{
		CavidadeFundoArredondado cavidade = (CavidadeFundoArredondado)ws.getFeature();
		Axis2Placement3D position = cavidade.getPosition();
		EClosed_pocket eClosed_pocket = (EClosed_pocket)this.model.createEntityInstance(EClosed_pocket.class);
		eClosed_pocket.setIts_id(null, cavidade.getNome());
		eClosed_pocket.setIts_workpiece(null, eWorkpiece);
		
		EAxis2_placement_3d placement = this.createAxis2Placement3D(cavidade.getNome() + " placement", position.getCoordinates(), position.getAxis(), position.getRefDirection());
		eClosed_pocket.setFeature_placement(null, placement);
		
		AMachining_operation aMachining_operation = eClosed_pocket.createIts_operations(null);
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		Point3d pocketDepthLocation = new Point3d(0, 0, -cavidade.getProfundidade());
		eClosed_pocket.setDepth(null, this.createPlane(cavidade.getNome() + " depth location", this.createAxis2Placement3D(cavidade.getNome() + " depth", pocketDepthLocation, position.getAxis(), position.getRefDirection())));
				
		EPlanar_pocket_bottom_condition bottom_condition = (EPlanar_pocket_bottom_condition)this.model.createEntityInstance(EPlanar_pocket_bottom_condition.class);
		
		eClosed_pocket.setBottom_condition(null, bottom_condition);
		eClosed_pocket.setOrthogonal_radius(null, this.createTolerancedLengthMeasure(cavidade.getVerticeRaio(), cavidade.getTolerancia()));
		eClosed_pocket.setPlanar_radius(null, this.createTolerancedLengthMeasure(cavidade.getFundoRaio(), cavidade.getTolerancia()));
		eClosed_pocket.setFeature_boundary(null, this.createRectangularClosedProfile(cavidade));
		eClosed_pocket.createIts_boss(null);
		return eClosed_pocket;
	}
	private ERound_hole createConicHole(Workingstep ws, EWorkpiece eWorkpiece, AMachining_operation operations) throws SdaiException
	{
		FuroConico furo = (FuroConico)ws.getFeature();
		Axis2Placement3D position = furo.getPosition();
		ERound_hole eRound_hole = (ERound_hole)this.model.createEntityInstance(ERound_hole.class);
		eRound_hole.setIts_id(null, furo.getNome());
		eRound_hole.setIts_workpiece(null, eWorkpiece);
//		position.setCoordinates(new Point3d(position.getCoordinates().x, position.getCoordinates().y, position.getCoordinates().z + this.workpieceHight));
		eRound_hole.setFeature_placement(null, this.createAxis2Placement3D(position.getName(), position.getCoordinates(), position.getAxis(), position.getRefDirection()));
		
		AMachining_operation aMachining_operation = eRound_hole.createIts_operations(null);
		
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		ArrayList<Double> holeDepthLocation = new ArrayList<Double>();
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(-furo.getProfundidade());
		
		eRound_hole.setDepth(null, this.createPlane("hole depth plane", this.createAxis2Placement3D("", holeDepthLocation, position.getAxis(), position.getRefDirection())));
		eRound_hole.setDiameter(null, this.createTolerancedLengthMeasure(furo.getDiametro(), furo.getTolerancia()));
		
		EHole_bottom_condition hole_bottom = null;
		if(furo.isPassante() == false)
		{
			hole_bottom = (EFlat_hole_bottom)this.model.createEntityInstance(EFlat_hole_bottom.class);
		} else if(furo.isPassante() == true)
		{
			hole_bottom = (EThrough_bottom_condition)this.model.createEntityInstance(EThrough_bottom_condition.class);
		}
		eRound_hole.setBottom_condition(null, hole_bottom);
		
		EDiameter_taper eDiameter_taper = (EDiameter_taper)this.model.createEntityInstance(EDiameter_taper.class);
		eDiameter_taper.setFinal_diameter(null, this.createTolerancedLengthMeasure(furo.getRaio1() * 2));
		eRound_hole.setChange_in_diameter(null, eDiameter_taper);
		return eRound_hole;
	}
	private ERound_hole createSphericalBottomHole(Workingstep ws, EWorkpiece eWorkpiece, AMachining_operation operations) throws SdaiException
	{
		FuroBaseEsferica furo = (FuroBaseEsferica)ws.getFeature();
		Axis2Placement3D position = furo.getPosition();
		ERound_hole eRound_hole = (ERound_hole)this.model.createEntityInstance(ERound_hole.class);
		eRound_hole.setIts_id(null, furo.getNome());
		eRound_hole.setIts_workpiece(null, eWorkpiece);
//		position.setCoordinates(new Point3d(position.getCoordinates().x, position.getCoordinates().y, position.getCoordinates().z + this.workpieceHight));
		eRound_hole.setFeature_placement(null, this.createAxis2Placement3D(position.getName(), position.getCoordinates(), position.getAxis(), position.getRefDirection()));
		
		AMachining_operation aMachining_operation = eRound_hole.createIts_operations(null);
		
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		ArrayList<Double> holeDepthLocation = new ArrayList<Double>();
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(-furo.getProfundidade());
		
		eRound_hole.setDepth(null, this.createPlane("hole depth plane", this.createAxis2Placement3D("", holeDepthLocation, position.getAxis(), position.getRefDirection())));
		eRound_hole.setDiameter(null, this.createTolerancedLengthMeasure(furo.getDiametro(), furo.getTolerancia()));
		
		ESpherical_hole_bottom eSpherical_hole_bottom = (ESpherical_hole_bottom)this.model.createEntityInstance(ESpherical_hole_bottom.class);
		eSpherical_hole_bottom.setRadius(null, this.createTolerancedLengthMeasure(furo.getRaio()));
		eRound_hole.setBottom_condition(null, eSpherical_hole_bottom);
		
		return eRound_hole;
	}
	private ERound_hole createFlatWithRadiusBottomHole(Workingstep ws, EWorkpiece eWorkpiece, AMachining_operation operations) throws SdaiException
	{
		FuroBaseArredondada furo = (FuroBaseArredondada)ws.getFeature();
		Axis2Placement3D position = furo.getPosition();
		ERound_hole eRound_hole = (ERound_hole)this.model.createEntityInstance(ERound_hole.class);
		eRound_hole.setIts_id(null, furo.getNome());
		eRound_hole.setIts_workpiece(null, eWorkpiece);
//		position.setCoordinates(new Point3d(position.getCoordinates().x, position.getCoordinates().y, position.getCoordinates().z + this.workpieceHight));
		eRound_hole.setFeature_placement(null, this.createAxis2Placement3D(position.getName(), position.getCoordinates(), position.getAxis(), position.getRefDirection()));
		
		AMachining_operation aMachining_operation = eRound_hole.createIts_operations(null);
		
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		ArrayList<Double> holeDepthLocation = new ArrayList<Double>();
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(-furo.getProfundidade());
		
		eRound_hole.setDepth(null, this.createPlane("hole depth plane", this.createAxis2Placement3D("", holeDepthLocation, position.getAxis(), position.getRefDirection())));
		eRound_hole.setDiameter(null, this.createTolerancedLengthMeasure(furo.getDiametro(), furo.getTolerancia()));
		
		EFlat_with_radius_hole_bottom eFlat_with_radius_hole_bottom = (EFlat_with_radius_hole_bottom)this.model.createEntityInstance(EFlat_with_radius_hole_bottom.class);
		eFlat_with_radius_hole_bottom.setCorner_radius(null, this.createTolerancedLengthMeasure(furo.getR1()));
		
		eRound_hole.setBottom_condition(null, eFlat_with_radius_hole_bottom);
		return eRound_hole;
	}
	private ERound_hole createConicalBottomRoundHole(Workingstep ws, EWorkpiece eWorkpiece, AMachining_operation operations) throws SdaiException
	{
		FuroBaseConica furo = (FuroBaseConica)ws.getFeature();
		Axis2Placement3D position = furo.getPosition();
		
		ERound_hole eRound_hole = (ERound_hole)this.model.createEntityInstance(ERound_hole.class);
		eRound_hole.setIts_id(null, furo.getNome());
		eRound_hole.setIts_workpiece(null, eWorkpiece);
//		position.setCoordinates(new Point3d(position.getCoordinates().x, position.getCoordinates().y, position.getCoordinates().z + this.workpieceHight));
		eRound_hole.setFeature_placement(null, this.createAxis2Placement3D(position.getName(), position.getCoordinates(), position.getAxis(), position.getRefDirection()));
		
		AMachining_operation aMachining_operation = eRound_hole.createIts_operations(null);
		
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		
		ArrayList<Double> holeDepthLocation = new ArrayList<Double>();
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(-furo.getProfundidade());

		eRound_hole.setDepth(null, this.createPlane("hole depth plane", this.createAxis2Placement3D("hole depth", holeDepthLocation, position.getAxis(), position.getRefDirection())));
		eRound_hole.setDiameter(null, this.createTolerancedLengthMeasure(furo.getDiametro(), furo.getTolerancia()));
		
		EConical_hole_bottom eConical_hole_bottom = (EConical_hole_bottom)this.model.createEntityInstance(EConical_hole_bottom.class);
		eConical_hole_bottom.setTip_angle(null, furo.getTipAngle() * Math.PI / 180);
		eRound_hole.setBottom_condition(null, eConical_hole_bottom);
		return eRound_hole;
	}
	private ERound_hole createConicalBottomRoundHole(String id, EWorkpiece eWorkpiece, AMachining_operation operations, EAxis2_placement_3d placement, double depth, double tipAngle, double diameter) throws SdaiException
	{
		ERound_hole eRound_hole = (ERound_hole)this.model.createEntityInstance(ERound_hole.class);
		eRound_hole.setIts_id(null, id);
		eRound_hole.setIts_workpiece(null, eWorkpiece);
		eRound_hole.setFeature_placement(null, placement);
		
		AMachining_operation aMachining_operation = eRound_hole.createIts_operations(null);
		
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		
		ArrayList<Double> holeDepthLocation = new ArrayList<Double>();
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(-depth);

		eRound_hole.setDepth(null, createPlane("hole depth plane", createAxis2Placement3D("hole depth", holeDepthLocation, featureAxis, featureRefDirection)));
		eRound_hole.setDiameter(null, createTolerancedLengthMeasure(diameter, pocketTolerance));
		
	
		EConical_hole_bottom eConical_hole_bottom = (EConical_hole_bottom)model.createEntityInstance(EConical_hole_bottom.class);
		eConical_hole_bottom.setTip_angle(null, tipAngle);
		eRound_hole.setBottom_condition(null, eConical_hole_bottom);
		return eRound_hole;
	}
	private ERound_hole createFlatBottomRoundHole(Workingstep ws, EWorkpiece eWorkpiece, AMachining_operation operations) throws SdaiException
	{
		FuroBasePlana furo = (FuroBasePlana)ws.getFeature();
		Axis2Placement3D position = furo.getPosition();
		ERound_hole eRound_hole = (ERound_hole)this.model.createEntityInstance(ERound_hole.class);
		eRound_hole.setIts_id(null, furo.getNome());
		eRound_hole.setIts_workpiece(null, eWorkpiece);
		
		EAxis2_placement_3d placement = this.createAxis2Placement3D(position.getName(), position.getCoordinates(), position.getAxis(), position.getRefDirection());
		eRound_hole.setFeature_placement(null, placement);
		
		AMachining_operation aMachining_operation = eRound_hole.createIts_operations(null);
		
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		ArrayList<Double> holeDepthLocation = new ArrayList<Double>();
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(-furo.getProfundidade());
		eRound_hole.setDiameter(null, createTolerancedLengthMeasure(furo.getDiametro(), furo.getTolerancia()));
		EPlane depthPlane = this.createPlane("hole depth plane", createAxis2Placement3D("hole depth", holeDepthLocation, position.getAxis(), position.getRefDirection()));
		eRound_hole.setDepth(null, depthPlane);
		EFlat_hole_bottom eFlat_hole_bottom = (EFlat_hole_bottom)model.createEntityInstance(EFlat_hole_bottom.class);
		eRound_hole.setBottom_condition(null, eFlat_hole_bottom);
		return eRound_hole;
	}
	private ERound_hole createFlatBottomRoundHole(String id, EWorkpiece eWorkpiece, AMachining_operation operations, EAxis2_placement_3d placement, double depth, double diameter) throws SdaiException
	{
		ERound_hole eRound_hole = (ERound_hole)this.model.createEntityInstance(ERound_hole.class);
		eRound_hole.setIts_id(null, id);
		eRound_hole.setIts_workpiece(null, eWorkpiece);
		eRound_hole.setFeature_placement(null, placement);
		
		AMachining_operation aMachining_operation = eRound_hole.createIts_operations(null);
		
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		ArrayList<Double> holeDepthLocation = new ArrayList<Double>();
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(-depth);
		eRound_hole.setDiameter(null, createTolerancedLengthMeasure(diameter, globalTolerance));
		EPlane depthPlane = this.createPlane("hole depth plane", createAxis2Placement3D("hole depth", holeDepthLocation, featureAxis, featureRefDirection));
		eRound_hole.setDepth(null, depthPlane);
		EFlat_hole_bottom eFlat_hole_bottom = (EFlat_hole_bottom)model.createEntityInstance(EFlat_hole_bottom.class);
		eRound_hole.setBottom_condition(null, eFlat_hole_bottom);
		return eRound_hole;
	}
	private ERound_hole createThroughBottomRoundHole(String id, EWorkpiece eWorkpiece, AMachining_operation operations, EAxis2_placement_3d placement, double depth, double diameter) throws SdaiException
	{
		ERound_hole eRound_hole = (ERound_hole)model.createEntityInstance(ERound_hole.class);
		eRound_hole.setIts_id(null, id);
		eRound_hole.setIts_workpiece(null, eWorkpiece);
		eRound_hole.setFeature_placement(null, placement);
		
		AMachining_operation aMachining_operation = eRound_hole.createIts_operations(null);
		
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		ArrayList<Double> holeDepthLocation = new ArrayList<Double>();
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(-depth);
		eRound_hole.setDiameter(null, createTolerancedLengthMeasure(diameter, globalTolerance));
		EPlane depthPlane = this.createPlane("hole depth plane", createAxis2Placement3D("hole depth", holeDepthLocation, featureAxis, featureRefDirection));
		eRound_hole.setDepth(null, depthPlane);
		EThrough_bottom_condition eThrough_bottom_condition = (EThrough_bottom_condition)model.createEntityInstance(EThrough_bottom_condition.class);
		eRound_hole.setBottom_condition(null, eThrough_bottom_condition);
		return eRound_hole;
	}
	private ERound_hole createThroughBottomRoundHole(Workingstep ws, EWorkpiece eWorkpiece, AMachining_operation operations) throws SdaiException
	{
		FuroBasePlana furo = (FuroBasePlana)ws.getFeature();
		Axis2Placement3D position = furo.getPosition();
//		position.setCoordinates(new Point3d(position.getCoordinates().x, position.getCoordinates().y, position.getCoordinates().z + this.workpieceHight));
		ERound_hole eRound_hole = (ERound_hole)this.model.createEntityInstance(ERound_hole.class);
		eRound_hole.setIts_id(null, furo.getNome());
		eRound_hole.setIts_workpiece(null, eWorkpiece);
		
		EAxis2_placement_3d placement = this.createAxis2Placement3D(position.getName(), position.getCoordinates(), position.getAxis(), position.getRefDirection());
		eRound_hole.setFeature_placement(null, placement);
		
		AMachining_operation aMachining_operation = eRound_hole.createIts_operations(null);
		
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		ArrayList<Double> holeDepthLocation = new ArrayList<Double>();
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(-furo.getProfundidade());
		eRound_hole.setDiameter(null, this.createTolerancedLengthMeasure(furo.getDiametro(), furo.getTolerancia()));
		EPlane depthPlane = this.createPlane("hole depth plane", createAxis2Placement3D("hole depth", holeDepthLocation, position.getAxis(), position.getRefDirection()));
		eRound_hole.setDepth(null, depthPlane);
		EThrough_bottom_condition eThrough_bottom_condition = (EThrough_bottom_condition)this.model.createEntityInstance(EThrough_bottom_condition.class);
		eRound_hole.setBottom_condition(null, eThrough_bottom_condition);
		return eRound_hole;
	}
	private ESlot createBezierProfileSlot(Workingstep ws, EWorkpiece eWorkpiece, AMachining_operation operations) throws SdaiException
	{
		RanhuraPerfilBezier ranhura = (RanhuraPerfilBezier)ws.getFeature();
		Axis2Placement3D position = ranhura.getPosition();
		ESlot eSlot = (ESlot)this.model.createEntityInstance(ESlot.class);
		eSlot.setIts_id(null, ranhura.getNome());
		eSlot.setIts_workpiece(null, eWorkpiece);
		
		EAxis2_placement_3d placement = this.createAxis2Placement3D(position.getName(), position.getCoordinates(), position.getAxis(), position.getRefDirection());
		eSlot.setFeature_placement(null, placement);
		
		AMachining_operation aMachining_operation = eSlot.createIts_operations(null);
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		Point3d slotDepthLocation = new Point3d(0, 0, -ranhura.getProfundidade());
		eSlot.setDepth(null, this.createPlane(ranhura.getNome() + " depth location", this.createAxis2Placement3D(ranhura.getNome() + " depth", slotDepthLocation, position.getAxis(), position.getRefDirection())));
		
		// course_of_travel inicia na origem do S. C. da feature, e os seus eixos coincidem com os da feature
		Point3d courseTravelLocation = new Point3d(0, 0, 0);
		ArrayList<Double> courseOfTravelAxis = new ArrayList<Double>();
		ArrayList<Double> courseOfTravelRefDirection = new ArrayList<Double>();
		courseOfTravelAxis.add(0.0);
		courseOfTravelAxis.add(0.0);
		courseOfTravelAxis.add(1.0);
		courseOfTravelRefDirection.add(1.0);
		courseOfTravelRefDirection.add(0.0);
		courseOfTravelRefDirection.add(0.0);
		eSlot.setCourse_of_travel(null, this.createLinearPath(this.createAxis2Placement3D("course of travel placement", courseTravelLocation, courseOfTravelAxis, courseOfTravelRefDirection), ranhura.getComprimento(), ranhura.getTolerancia(), ranhura.getEixo()));			
		eSlot.setSwept_shape(null, this.createBezierProfile(ranhura));
		ASlot_end_type aSlot_end_type = eSlot.createEnd_conditions(null);
		EOpen_slot_end_type end_type = (EOpen_slot_end_type)this.model.createEntityInstance(EOpen_slot_end_type.class);
		EOpen_slot_end_type end_type1 = (EOpen_slot_end_type)this.model.createEntityInstance(EOpen_slot_end_type.class);
		aSlot_end_type.addUnordered(end_type);
		aSlot_end_type.addUnordered(end_type1);
		return eSlot;
	}
	private ESlot createVeeProfileSlot(Workingstep ws, EWorkpiece eWorkpiece, AMachining_operation operations) throws SdaiException
	{
		RanhuraPerfilVee ranhura = (RanhuraPerfilVee)ws.getFeature();
		Axis2Placement3D position = ranhura.getPosition();
		ESlot eSlot = (ESlot)this.model.createEntityInstance(ESlot.class);
		eSlot.setIts_id(null, ranhura.getNome());
		eSlot.setIts_workpiece(null, eWorkpiece);
		
		EAxis2_placement_3d placement = this.createAxis2Placement3D(position.getName(), position.getCoordinates(), position.getAxis(), position.getRefDirection());
		eSlot.setFeature_placement(null, placement);
		
		AMachining_operation aMachining_operation = eSlot.createIts_operations(null);
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		Point3d slotDepthLocation = new Point3d(0, 0, -ranhura.getProfundidade());
		eSlot.setDepth(null, this.createPlane(ranhura.getNome() + " depth location", this.createAxis2Placement3D(ranhura.getNome() + " depth", slotDepthLocation, position.getAxis(), position.getRefDirection())));
		Point3d courseTravelLocation = new Point3d(0, 0, 0);
		ArrayList<Double> courseOfTravelAxis = new ArrayList<Double>();
		ArrayList<Double> courseOfTravelRefDirection = new ArrayList<Double>();
		
		courseOfTravelAxis.add(0.0);
		courseOfTravelAxis.add(0.0);
		courseOfTravelAxis.add(1.0);
		courseOfTravelRefDirection.add(1.0);
		courseOfTravelRefDirection.add(0.0);
		courseOfTravelRefDirection.add(0.0);
		eSlot.setCourse_of_travel(null, this.createLinearPath(this.createAxis2Placement3D("course of travel placement", courseTravelLocation, courseOfTravelAxis, courseOfTravelRefDirection), ranhura.getComprimento(), ranhura.getTolerancia(), ranhura.getEixo()));			
		eSlot.setSwept_shape(null, this.createVeeProfile(ranhura));
		ASlot_end_type aSlot_end_type = eSlot.createEnd_conditions(null);
		EOpen_slot_end_type end_type = (EOpen_slot_end_type)this.model.createEntityInstance(EOpen_slot_end_type.class);
		EOpen_slot_end_type end_type1 = (EOpen_slot_end_type)this.model.createEntityInstance(EOpen_slot_end_type.class);
		aSlot_end_type.addUnordered(end_type);
		aSlot_end_type.addUnordered(end_type1);
		return eSlot;
	}
	private ESlot createRoundedUProfileSlot(Workingstep ws, EWorkpiece eWorkpiece, AMachining_operation operations) throws SdaiException
	{
		RanhuraPerfilRoundedU ranhura = (RanhuraPerfilRoundedU)ws.getFeature();
		Axis2Placement3D position = ranhura.getPosition();
		ESlot eSlot = (ESlot)this.model.createEntityInstance(ESlot.class);
		eSlot.setIts_id(null, ranhura.getNome());
		eSlot.setIts_workpiece(null, eWorkpiece);
		
		EAxis2_placement_3d placement = this.createAxis2Placement3D(position.getName(), position.getCoordinates(), position.getAxis(), position.getRefDirection());
		eSlot.setFeature_placement(null, placement);
		
		AMachining_operation aMachining_operation = eSlot.createIts_operations(null);
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		Point3d slotDepthLocation = new Point3d(0, 0, -ranhura.getProfundidade());
		eSlot.setDepth(null, this.createPlane(ranhura.getNome() + " depth location", this.createAxis2Placement3D(ranhura.getNome() + " depth", slotDepthLocation, position.getAxis(), position.getRefDirection())));
		
		// course_of_travel inicia na origem do S.C. da feature e os eixos coincidem para ranhuras horizontais e verticais
		Point3d courseTravelLocation = new Point3d(0, 0, 0);
		ArrayList<Double> courseOfTravelAxis = new ArrayList<Double>();
		ArrayList<Double> courseOfTravelRefDirection = new ArrayList<Double>();
		courseOfTravelAxis.add(0.0);
		courseOfTravelAxis.add(0.0);
		courseOfTravelAxis.add(1.0);
		courseOfTravelRefDirection.add(1.0);
		courseOfTravelRefDirection.add(0.0);
		courseOfTravelRefDirection.add(0.0);
		
		eSlot.setCourse_of_travel(null, this.createLinearPath(this.createAxis2Placement3D("course of travel placement", courseTravelLocation, courseOfTravelAxis, courseOfTravelRefDirection), ranhura.getComprimento(), ranhura.getTolerancia(), ranhura.getEixo()));			
		eSlot.setSwept_shape(null, this.createRoundedUProfile(ranhura));
		ASlot_end_type aSlot_end_type = eSlot.createEnd_conditions(null);
		EOpen_slot_end_type end_type = (EOpen_slot_end_type)this.model.createEntityInstance(EOpen_slot_end_type.class);
		EOpen_slot_end_type end_type1 = (EOpen_slot_end_type)this.model.createEntityInstance(EOpen_slot_end_type.class);
		aSlot_end_type.addUnordered(end_type);
		aSlot_end_type.addUnordered(end_type1);
		return eSlot;
	}
	private ESlot createCircularPartialProfileSlot(Workingstep ws, EWorkpiece eWorkpiece, AMachining_operation operations) throws SdaiException
	{
		RanhuraPerfilCircularParcial ranhura = (RanhuraPerfilCircularParcial)ws.getFeature();
		Axis2Placement3D position = ranhura.getPosition();
		ESlot eSlot = (ESlot)this.model.createEntityInstance(ESlot.class);
		eSlot.setIts_id(null, ranhura.getNome());
		eSlot.setIts_workpiece(null, eWorkpiece);
		
		EAxis2_placement_3d placement = this.createAxis2Placement3D(position.getName(), position.getCoordinates(), position.getAxis(), position.getRefDirection());
		eSlot.setFeature_placement(null, placement);
		
		AMachining_operation aMachining_operation = eSlot.createIts_operations(null);
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		Point3d slotDepthLocation = new Point3d(0, 0, -ranhura.getProfundidade());
		eSlot.setDepth(null, this.createPlane(ranhura.getNome() + " depth location", this.createAxis2Placement3D(ranhura.getNome() + " depth", slotDepthLocation, position.getAxis(), position.getRefDirection())));
		
		
		// o placement do course_of_travel é igual pra horizontal e pra vertical (comeca na origem do Sistema de Coordenadas da feature)
		Point3d courseTravelLocation = new Point3d(0, 0, 0);
		ArrayList<Double> courseOfTravelAxis = new ArrayList<Double>();
		ArrayList<Double> courseOfTravelRefDirection = new ArrayList<Double>();
		courseOfTravelAxis.add(0.0);
		courseOfTravelAxis.add(0.0);
		courseOfTravelAxis.add(1.0);
		courseOfTravelRefDirection.add(1.0);
		courseOfTravelRefDirection.add(0.0);
		courseOfTravelRefDirection.add(0.0);
		
		eSlot.setCourse_of_travel(null, this.createLinearPath(this.createAxis2Placement3D("course of travel placement", courseTravelLocation, courseOfTravelAxis, courseOfTravelRefDirection), ranhura.getComprimento(), ranhura.getTolerancia(), ranhura.getEixo()));			
		eSlot.setSwept_shape(null, this.createPartialCircularProfile(ranhura));
		ASlot_end_type aSlot_end_type = eSlot.createEnd_conditions(null);
		EOpen_slot_end_type end_type = (EOpen_slot_end_type)this.model.createEntityInstance(EOpen_slot_end_type.class);
		EOpen_slot_end_type end_type1 = (EOpen_slot_end_type)this.model.createEntityInstance(EOpen_slot_end_type.class);
		aSlot_end_type.addUnordered(end_type);
		aSlot_end_type.addUnordered(end_type1);
		return eSlot;
	}
	private ESlot createSquareUProfileSlot(Workingstep ws, EWorkpiece eWorkpiece, AMachining_operation operations) throws SdaiException
	{
		RanhuraPerfilQuadradoU ranhura = (RanhuraPerfilQuadradoU)ws.getFeature();
		Axis2Placement3D position = ranhura.getPosition();
		//position.setCoordinates(new Point3d(position.getCoordinates().x, position.getCoordinates().y, position.getCoordinates().z + this.workpieceHight));
		ESlot eSlot = (ESlot)this.model.createEntityInstance(ESlot.class);
		eSlot.setIts_id(null, ranhura.getNome());
		eSlot.setIts_workpiece(null, eWorkpiece);

		EAxis2_placement_3d placement = this.createAxis2Placement3D(position.getName(), position.getCoordinates(), position.getAxis(), position.getRefDirection());
		eSlot.setFeature_placement(null, placement);
		
		AMachining_operation aMachining_operation = eSlot.createIts_operations(null);
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		Point3d slotDepthLocation = new Point3d(0, 0, -ranhura.getProfundidade());
		eSlot.setDepth(null, this.createPlane(ranhura.getNome() + " depth location", this.createAxis2Placement3D(ranhura.getNome() + " depth", slotDepthLocation, position.getAxis(), position.getRefDirection())));
		
		Point3d courseTravelLocation = new Point3d(0, 0, 0);
		ArrayList<Double> courseOfTravelAxis = new ArrayList<Double>();
		ArrayList<Double> courseOfTravelRefDirection = new ArrayList<Double>();
		courseOfTravelAxis.add(0.0);
		courseOfTravelAxis.add(0.0);
		courseOfTravelAxis.add(1.0);
		courseOfTravelRefDirection.add(1.0);
		courseOfTravelRefDirection.add(0.0);
		courseOfTravelRefDirection.add(0.0);
		
		eSlot.setCourse_of_travel(null, this.createLinearPath(this.createAxis2Placement3D("course of travel placement", courseTravelLocation, courseOfTravelAxis, courseOfTravelRefDirection), ranhura.getComprimento(), ranhura.getTolerancia(), ranhura.getEixo()));			
		
		eSlot.setSwept_shape(null, this.createSquareUProfile(ranhura));
		ASlot_end_type aSlot_end_type = eSlot.createEnd_conditions(null);
		EOpen_slot_end_type end_type = (EOpen_slot_end_type)this.model.createEntityInstance(EOpen_slot_end_type.class);
		EOpen_slot_end_type end_type1 = (EOpen_slot_end_type)this.model.createEntityInstance(EOpen_slot_end_type.class);
		aSlot_end_type.addUnordered(end_type);
		aSlot_end_type.addUnordered(end_type1);
		return eSlot;
	}
	private ESlot createSlot(Workingstep ws, EWorkpiece eWorkpiece, AMachining_operation operations) throws SdaiException
	{
		Ranhura ranhura = (Ranhura)ws.getFeature();
		Axis2Placement3D position = ranhura.getPosition();
//		position.setCoordinates(new Point3d(position.getCoordinates().x, position.getCoordinates().y, position.getCoordinates().z + this.workpieceHight));
		ESlot eSlot = (ESlot)this.model.createEntityInstance(ESlot.class);
		eSlot.setIts_id(null, ranhura.getNome());
		eSlot.setIts_workpiece(null, eWorkpiece);
		
		EAxis2_placement_3d placement = this.createAxis2Placement3D(position.getName(), position.getCoordinates(), position.getAxis(), position.getRefDirection());
		eSlot.setFeature_placement(null, placement);

		AMachining_operation aMachining_operation = eSlot.createIts_operations(null);
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		Point3d slotDepthLocation = new Point3d(0, 0, -ranhura.getProfundidade());
		eSlot.setDepth(null, this.createPlane(ranhura.getNome() + " depth location", this.createAxis2Placement3D("Slot depth", slotDepthLocation, position.getAxis(), position.getRefDirection())));
		
		Point3d courseTravelLocation = new Point3d(0, 0, 0);
		ArrayList<Double> courseOfTravelAxis = new ArrayList<Double>();
		ArrayList<Double> courseOfTravelRefDirection = new ArrayList<Double>();
		courseOfTravelAxis.add(0.0);
		courseOfTravelAxis.add(0.0);
		courseOfTravelAxis.add(1.0);
		courseOfTravelRefDirection.add(1.0);
		courseOfTravelRefDirection.add(0.0);
		courseOfTravelRefDirection.add(0.0);

		eSlot.setCourse_of_travel(null, this.createLinearPath(this.createAxis2Placement3D("course of travel placement", courseTravelLocation, courseOfTravelAxis, courseOfTravelRefDirection), ranhura.getComprimento(), ranhura.getTolerancia(), ranhura.getEixo()));
		
		eSlot.setSwept_shape(null, this.createSquareUProfile(ranhura)); // este metodo tambem cria este perfil
		ASlot_end_type aSlot_end_type = eSlot.createEnd_conditions(null);
		EOpen_slot_end_type end_type = (EOpen_slot_end_type)model.createEntityInstance(EOpen_slot_end_type.class);
		EOpen_slot_end_type end_type1 = (EOpen_slot_end_type)model.createEntityInstance(EOpen_slot_end_type.class);
		aSlot_end_type.addUnordered(end_type);
		aSlot_end_type.addUnordered(end_type1);
		return eSlot;
	}
	private ESlot createSlot(String id, EWorkpiece eWorkpiece, AMachining_operation operations, EAxis2_placement_3d placement, double width, double depth, int direction) throws SdaiException
	{
		ESlot eSlot = (ESlot)model.createEntityInstance(ESlot.class);
		eSlot.setIts_id(null, id);
		eSlot.setIts_workpiece(null, eWorkpiece);
		
		AMachining_operation aMachining_operation = eSlot.createIts_operations(null);
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		
		ArrayList<Double> slotDepthLocation = new ArrayList<Double>();
		slotDepthLocation.add(0.0);
		slotDepthLocation.add(0.0);
		slotDepthLocation.add(-depth);
		eSlot.setFeature_placement(null, placement);
		eSlot.setDepth(null, createPlane("Slot depth", createAxis2Placement3D("Slot depth", slotDepthLocation, this.featureAxis, this.featureRefDirection)));
		
		ArrayList<Double> courseTravelLocation = new ArrayList<Double>();
		courseTravelLocation.add(0.0);
		courseTravelLocation.add(0.0);
		courseTravelLocation.add(0.0);
		
		ArrayList<Double> sweepShapeAxis = new ArrayList<Double>();
		ArrayList<Double> sweepShapeRefDirection = new ArrayList<Double>();
		EAxis2_placement_3d placementProfile = null;
		if(direction == Ranhura.HORIZONTAL)
		{
			sweepShapeAxis.add(1.0);
			sweepShapeAxis.add(0.0);
			sweepShapeAxis.add(0.0);
			
			sweepShapeRefDirection.add(0.0);
			sweepShapeRefDirection.add(0.0);
			sweepShapeRefDirection.add(1.0);

			eSlot.setCourse_of_travel(null, createLinearPath(createAxis2Placement3D("", courseTravelLocation, sweepShapeAxis, sweepShapeRefDirection), workpieceLength, direction));
			placementProfile = createAxis2Placement3D("profile slot location", courseTravelLocation, sweepShapeAxis, sweepShapeRefDirection);
		} else if(direction == Ranhura.VERTICAL)
		{
			sweepShapeAxis.add(0.0);
			sweepShapeAxis.add(1.0);
			sweepShapeAxis.add(0.0);
			
			sweepShapeRefDirection.add(0.0);
			sweepShapeRefDirection.add(0.0);
			sweepShapeRefDirection.add(1.0);
			eSlot.setCourse_of_travel(null, createLinearPath(createAxis2Placement3D("", courseTravelLocation, sweepShapeAxis, sweepShapeRefDirection), workpiecewidth, direction));	
			placementProfile = createAxis2Placement3D("profile slot location", courseTravelLocation, sweepShapeAxis, sweepShapeRefDirection);
		}
		
		eSlot.setSwept_shape(null, createSquareUProfile(placementProfile, width));
		ASlot_end_type aSlot_end_type = eSlot.createEnd_conditions(null);
		EOpen_slot_end_type end_type = (EOpen_slot_end_type)model.createEntityInstance(EOpen_slot_end_type.class);
		EOpen_slot_end_type end_type1 = (EOpen_slot_end_type)model.createEntityInstance(EOpen_slot_end_type.class);
		aSlot_end_type.addUnordered(end_type);
		aSlot_end_type.addUnordered(end_type1);

		return eSlot;
	}
	private EStep createStep(Workingstep ws, EWorkpiece eWorkpiece, AMachining_operation operations) throws SdaiException
	{
		Degrau degrau = (Degrau)ws.getFeature();
		Axis2Placement3D position = degrau.getPosition();
		EStep eStep = (EStep)this.model.createEntityInstance(EStep.class);
		eStep.setIts_id(null, degrau.getNome());
		eStep.setIts_workpiece(null, eWorkpiece);
		
		EAxis2_placement_3d placement = this.createAxis2Placement3D(position.getName(), position.getCoordinates(), position.getAxis(), position.getRefDirection());
		eStep.setFeature_placement(null, placement);
		
		AMachining_operation aMachining_operation = eStep.createIts_operations(null);
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		ArrayList<Double> depthAxis = new ArrayList<Double>();
		ArrayList<Double> depthRefDirection = new ArrayList<Double>();
		depthAxis.add(0.0);
		depthAxis.add(0.0);
		depthAxis.add(1.0);
		depthRefDirection.add(1.0);
		depthRefDirection.add(0.0);
		depthRefDirection.add(0.0);
		Point3d stepDepthLocation = new Point3d(0, 0, -degrau.getProfundidade());
		eStep.setDepth(null, this.createPlane(degrau.getNome() + " - depth location", this.createAxis2Placement3D(degrau.getNome() + " depth", stepDepthLocation, depthAxis, depthRefDirection)));
		
		Point3d linearPathLocation = new Point3d(0, 0, 0);
		
		ELinear_path openBoundary = (ELinear_path)this.model.createEntityInstance(ELinear_path.class);
		openBoundary.setPlacement(null, this.createAxis2Placement3D(degrau.getNome() +" open boundary location", linearPathLocation, depthAxis, depthRefDirection));
		openBoundary.setDistance(null, this.createTolerancedLengthMeasure(degrau.getComprimento(), degrau.getTolerancia()));
		ArrayList<Double> ratios = new ArrayList<Double>();
		ratios.add(0.0);
		ratios.add(1.0);
		ratios.add(0.0);
		openBoundary.setIts_direction(null, this.createDirection("", ratios)); // sempre aponta pro eixo Y no sistema de coordenadas da feature		
		eStep.setOpen_boundary(null, openBoundary);
		eStep.createIts_boss(null);
		
//		ArrayList<Double> wallProfAxis = new ArrayList<Double>();
//		wallProfAxis.add(0.0);
//		wallProfAxis.add(0.0);
//		wallProfAxis.add(1.0);
//		ArrayList<Double> wallRefDirection = new ArrayList<Double>();
//		wallRefDirection.add(1.0);
//		wallRefDirection.add(0.0);
//		wallRefDirection.add(0.0);
//		EAxis2_placement_3d veePlacement = this.createAxis2Placement3D("", linearPathLocation, wallProfAxis, wallRefDirection);
//		EVee_profile wallBoundary = this.createVeeProfile(veePlacement, 0, 90 * Math.PI/180, 90 * Math.PI/180);
//		eStep.setWall_boundary(null, wallBoundary);
		
		return eStep;
	}
	private EStep createStep(String id, EWorkpiece workpiece, AMachining_operation operations, EAxis2_placement_3d stepPlacement, double depth, int orientation) throws SdaiException
	{
		EStep eStep = (EStep)this.model.createEntityInstance(EStep.class);
		eStep.setIts_id(null, id);
		eStep.setIts_workpiece(null, workpiece);
		
		AMachining_operation aMachining_operation = eStep.createIts_operations(null);
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		eStep.setFeature_placement(null, stepPlacement);
		ArrayList<Double> depthLocation = new ArrayList<Double>();
		depthLocation.add(0.0);
		depthLocation.add(0.0);
		depthLocation.add(-depth);
		
		EAxis2_placement_3d stepDepthLocation = createAxis2Placement3D("step depth location", depthLocation, featureAxis, featureRefDirection);
		
		eStep.setDepth(null, createPlane("Step depth plane", stepDepthLocation));
		ArrayList<Double> linearPathLocation = new ArrayList<Double>();
		linearPathLocation.add(0.0);
		linearPathLocation.add(0.0);
		linearPathLocation.add(0.0);
		
		ELinear_path openBoundary = null;
		EAxis2_placement_3d linearPathL = createAxis2Placement3D("step linear path location", linearPathLocation, featureAxis, featureRefDirection);
		
		if (Degrau.HORIZONTAL == orientation)
		{
			openBoundary = createLinearPath(linearPathL, workpieceLength, Degrau.HORIZONTAL);
			
		} else if (Degrau.VERTICAL == orientation)
		{
			openBoundary = createLinearPath(linearPathL, workpieceLength, Degrau.VERTICAL);
		}
		eStep.setOpen_boundary(null, openBoundary);
		ArrayList<Double> wallProfAxis = new ArrayList<Double>();
		wallProfAxis.add(0.0);
		wallProfAxis.add(0.0);
		wallProfAxis.add(1.0);
		ArrayList<Double> wallRefDirection = new ArrayList<Double>();
		wallRefDirection.add(1.0);
		wallRefDirection.add(0.0);
		wallRefDirection.add(0.0);
		EAxis2_placement_3d veePlacement = createAxis2Placement3D("", linearPathLocation, wallProfAxis, wallRefDirection);
		EVee_profile wallBoundary = createVeeProfile(veePlacement, 0, 90 * Math.PI/180, 90 * Math.PI/180);
		eStep.setWall_boundary(null, wallBoundary);
		
		return eStep;
	}
	private ELinear_path createLinearPath(EAxis2_placement_3d placement, double distance, int itsDirection) throws SdaiException
	{
		ELinear_path eLinear_path = (ELinear_path)this.model.createEntityInstance(ELinear_path.class);
		eLinear_path.setPlacement(null, placement);
		eLinear_path.setDistance(null, this.createTolerancedLengthMeasure(distance));
		ArrayList<Double> ratios = new ArrayList<Double>();
		if (itsDirection == Ranhura.HORIZONTAL)
		{
			ratios.add(0.0);
			ratios.add(0.0);
			ratios.add(-1.0);
			EDirection eDirection = this.createDirection("Linear Path Direction - HORIZONTAL", ratios);
			eLinear_path.setIts_direction(null, eDirection);
		} else if (itsDirection == Ranhura.VERTICAL)
		{
			ratios.add(0.0);
			ratios.add(0.0);
			ratios.add(-1.0);
			EDirection eDirection = this.createDirection("Linear Path Direction - VERTICAL", ratios);
			eLinear_path.setIts_direction(null, eDirection);
		}
		return eLinear_path;
	}
	private ELinear_path createLinearPath(EAxis2_placement_3d placement, double distance, double tolerance, int direction) throws SdaiException
	{
		ELinear_path eLinear_path = (ELinear_path)this.model.createEntityInstance(ELinear_path.class);
		eLinear_path.setPlacement(null, placement);
		eLinear_path.setDistance(null, this.createTolerancedLengthMeasure(distance, tolerance));
		ArrayList<Double> ratios = new ArrayList<Double>();
		EDirection eDirection= null;
		if(direction == Ranhura.HORIZONTAL)
		{
			ratios.add(1.0);
			ratios.add(0.0);
			ratios.add(0.0);
			eDirection = this.createDirection("Linear Path Direction - HORIZONTAL", ratios);
		} else if (direction == Ranhura.VERTICAL)
		{
			ratios.add(0.0);
			ratios.add(1.0);
			ratios.add(0.0);
			eDirection = this.createDirection("Linear Path Direction - VERTICAL", ratios);
		}
		eLinear_path.setIts_direction(null, eDirection);
		return eLinear_path;
	}
	private EVee_profile createVeeProfile(EAxis2_placement_3d veePlaceemnt, double Profileradius, double profileAngle, double tiltAngle) throws SdaiException
	{
		EVee_profile eVee_profile = (EVee_profile)model.createEntityInstance(EVee_profile.class);
		EToleranced_length_measure eRadius = createTolerancedLengthMeasure(Profileradius);
		eVee_profile.setPlacement(null, veePlaceemnt);
		eVee_profile.setProfile_radius(null, eRadius);
		eVee_profile.setProfile_angle(null, profileAngle);
		eVee_profile.setTilt_angle(null, tiltAngle);
		
		return eVee_profile;
	}
	private EDrilling createDrillingOperation(Workingstep ws) throws SdaiException
	{
		Drilling drilling = (Drilling)ws.getOperation();
		CondicoesDeUsinagem condicoesDeUsinagem = ws.getCondicoesUsinagem();
		
		EDrilling eDrilling = (EDrilling)this.model.createEntityInstance(EDrilling.class);
		eDrilling.setIts_id(null, drilling.getId());
		eDrilling.setRetract_plane(null, drilling.getRetractPlane());
		eDrilling.setIts_tool(null, createTwistDrillTool(ws));
		eDrilling.setIts_technology(null, createTechnology(condicoesDeUsinagem.getF(), condicoesDeUsinagem.getN(), condicoesDeUsinagem.getVc()));
		eDrilling.setIts_machine_functions(null, createMachineFunctions(drilling.isCoolant()));
		if(drilling.getStartPoint() != null)
		{
			ArrayList<Double> coordinates = new ArrayList<Double>();
			coordinates.add(drilling.getStartPoint().x);
			coordinates.add(drilling.getStartPoint().y);
			coordinates.add(drilling.getStartPoint().z);
			ECartesian_point startPoint = this.createCartesianPoint("start point", coordinates);
			eDrilling.setStart_point(null, startPoint);
		}
		if(drilling.getCuttingDepth() != 0)
		{
			eDrilling.setCutting_depth(null, drilling.getCuttingDepth());
		}
		
		EDrilling_type_strategy eDrilling_type_strategy = (EDrilling_type_strategy)this.model.createEntityInstance(EDrilling_type_strategy.class);
		eDrilling.setIts_machining_strategy(null, eDrilling_type_strategy);
		
		return eDrilling;
	}
	private EReaming createReamingOperation(Workingstep ws) throws SdaiException
	{
		Reaming reaming = (Reaming)ws.getOperation();
		CondicoesDeUsinagem condicoesDeUsinagem = ws.getCondicoesUsinagem();
		EReaming eReaming = (EReaming)this.model.createEntityInstance(EReaming.class);
		if(reaming.getStartPoint() != null)
		{
			ArrayList<Double> coordinates = new ArrayList<Double>();
			coordinates.add(reaming.getStartPoint().x);
			coordinates.add(reaming.getStartPoint().y);
			coordinates.add(reaming.getStartPoint().z);
			ECartesian_point startPoint = this.createCartesianPoint("start point", coordinates);
			eReaming.setStart_point(null, startPoint);
			
		}
		if(reaming.getCuttingDepth() != 0)
		{
			eReaming.setCutting_depth(null, reaming.getCuttingDepth());			
		}
		eReaming.setIts_id(null, reaming.getId());
		eReaming.setRetract_plane(null, reaming.getRetractPlane());
		eReaming.setIts_tool(null, createReamerTool(ws));
		eReaming.setIts_technology(null, createTechnology(condicoesDeUsinagem.getF(), condicoesDeUsinagem.getN(), condicoesDeUsinagem.getVc()));
		eReaming.setIts_machine_functions(null, createMachineFunctions(reaming.isCoolant()));
		
		EDrilling_type_strategy eDrilling_type_strategy = (EDrilling_type_strategy)this.model.createEntityInstance(EDrilling_type_strategy.class);
		eReaming.setIts_machining_strategy(null, eDrilling_type_strategy);
		
		return eReaming;
	}
	private EBoring createBoringOperation(Workingstep ws) throws SdaiException
	{
		Boring boring = (Boring)ws.getOperation();
		CondicoesDeUsinagem cdu = ws.getCondicoesUsinagem();
		EBoring eBoring = (EBoring)this.model.createEntityInstance(EBoring.class);
		if(boring.getStartPoint() != null)
		{
			ArrayList<Double> coordinates = new ArrayList<Double>();
			coordinates.add(boring.getStartPoint().x);
			coordinates.add(boring.getStartPoint().y);
			coordinates.add(boring.getStartPoint().z);
			ECartesian_point startPoint = createCartesianPoint("start point", coordinates);
			eBoring.setStart_point(null, startPoint);
		}
		if(boring.getCuttingDepth() != 0)
		{
			eBoring.setCutting_depth(null, boring.getCuttingDepth());			
		}
		eBoring.setIts_id(null, boring.getId());
		eBoring.setRetract_plane(null, boring.getRetractPlane());
		eBoring.setIts_tool(null, createBoringTool(ws));
		eBoring.setIts_technology(null, this.createTechnology(cdu.getF(), cdu.getN(), cdu.getVc()));
		eBoring.setIts_machine_functions(null, this.createMachineFunctions(boring.isCoolant()));
		
		EDrilling_type_strategy eDrilling_type_strategy = (EDrilling_type_strategy)this.model.createEntityInstance(EDrilling_type_strategy.class);
		eBoring.setIts_machining_strategy(null, eDrilling_type_strategy);
		eBoring.setSpindle_stop_at_bottom(null, Boolean.FALSE);
		
		return eBoring;
	}
	private ECenter_drilling createCenterDrillingOperation(Workingstep ws) throws SdaiException
	{
		CenterDrilling centerDrilling = (CenterDrilling)ws.getOperation();
		CondicoesDeUsinagem cu = ws.getCondicoesUsinagem();
		ECenter_drilling eCenter_drilling = (ECenter_drilling)this.model.createEntityInstance(ECenter_drilling.class);
		if(centerDrilling.getStartPoint() !=null)
		{
			ArrayList<Double> coordinates = new ArrayList<Double>();
			coordinates.add(centerDrilling.getStartPoint().x);
			coordinates.add(centerDrilling.getStartPoint().y);
			coordinates.add(centerDrilling.getStartPoint().z);
			ECartesian_point startPoint = createCartesianPoint("start point", coordinates);
			eCenter_drilling.setStart_point(null, startPoint);
		}
		if(centerDrilling.getCuttingDepth() != 0)
		{
			eCenter_drilling.setCutting_depth(null, centerDrilling.getCuttingDepth());
		}
		eCenter_drilling.setIts_id(null, centerDrilling.getId());
		eCenter_drilling.setRetract_plane(null, centerDrilling.getRetractPlane());
		eCenter_drilling.setIts_tool(null, this.createCenterDrillTool(ws));
		eCenter_drilling.setIts_technology(null, this.createTechnology(cu.getF(), cu.getN(), cu.getVc()));
		eCenter_drilling.setIts_machine_functions(null, this.createMachineFunctions(centerDrilling.isCoolant()));
		
		EDrilling_type_strategy eDrilling_type_strategy = (EDrilling_type_strategy)this.model.createEntityInstance(EDrilling_type_strategy.class);
		eCenter_drilling.setIts_machining_strategy(null, eDrilling_type_strategy);
		
		return eCenter_drilling;
	}
	private EBottom_and_side_rough_milling createBottomAndSideRoughOperation (Workingstep ws) throws SdaiException
	{
		BottomAndSideRoughMilling bottomAndSideRoughMilling = (BottomAndSideRoughMilling)ws.getOperation();
		CondicoesDeUsinagem condicoesDeUsinagem = ws.getCondicoesUsinagem();
		EBottom_and_side_rough_milling eBottom_and_side_rough_milling = (EBottom_and_side_rough_milling)this.model.createEntityInstance(EBottom_and_side_rough_milling.class);
		eBottom_and_side_rough_milling.setIts_id(null, ws.getOperation().getId());
		eBottom_and_side_rough_milling.setRetract_plane(null, bottomAndSideRoughMilling.getRetractPlane());
		
		Ferramenta ferramenta = ws.getFerramenta();
		
		if(ferramenta.getClass()==FaceMill.class)
			eBottom_and_side_rough_milling.setIts_tool(null, createFaceMill(ws));
		else if(ferramenta.getClass()==EndMill.class)
			eBottom_and_side_rough_milling.setIts_tool(null, createEndMill(ws));
		else if(ferramenta.getClass()==BullnoseEndMill.class)
			eBottom_and_side_rough_milling.setIts_tool(null, createBullNoseEndMill(ws));
		else if(ferramenta.getClass()==BallEndMill.class)
			eBottom_and_side_rough_milling.setIts_tool(null, createBallEndMillTool(ws));
		
		eBottom_and_side_rough_milling.setIts_technology(null, createTechnology(condicoesDeUsinagem.getF(), condicoesDeUsinagem.getN(), condicoesDeUsinagem.getVc()));
		eBottom_and_side_rough_milling.setIts_machine_functions(null, createMachineFunctions(bottomAndSideRoughMilling.isCoolant()));
		eBottom_and_side_rough_milling.setStart_point(null, this.createCartesianPoint("start point", ws.getOperation().getStartPoint()));
		//eBottom_and_side_rough_milling.setOvercut_length(null, 0.00);
		
//		EPlunge_toolaxis strategyApproach = (EPlunge_toolaxis)model.createEntityInstance(EPlunge_toolaxis.class);
		ArrayList<Double> ratios = new ArrayList<Double>();
		ratios.add(ws.getFeature().getPosition().getAxis().get(0));
		ratios.add(ws.getFeature().getPosition().getAxis().get(1));
		ratios.add(ws.getFeature().getPosition().getAxis().get(2));
		if(ws.getFeature().getClass().equals(GeneralClosedPocket.class) || ws.getFeature().getClass().equals(Region.class))
		{
			EPlunge_strategy strategyApproach = createPlungeStrategy(bottomAndSideRoughMilling);
			eBottom_and_side_rough_milling.setApproach(null, strategyApproach);
		}
		EPlunge_toolaxis strategyRetract = (EPlunge_toolaxis)model.createEntityInstance(EPlunge_toolaxis.class);
		ArrayList<Double> ratios1 = new ArrayList<Double>();
		ratios1.add(ws.getFeature().getPosition().getAxis().get(0));
		ratios1.add(ws.getFeature().getPosition().getAxis().get(1));
		ratios1.add(-ws.getFeature().getPosition().getAxis().get(2));
		
//		EDirection approach = createDirection("approach strategy direction", ratios);
//		strategyApproach.setTool_orientation(null, approach);
		
		EDirection retract = createDirection("retract strategy direction", ratios1);
		strategyRetract.setTool_orientation(null, retract);
		eBottom_and_side_rough_milling.setRetract(null, strategyRetract);
		eBottom_and_side_rough_milling.setAxial_cutting_depth(null, condicoesDeUsinagem.getAp());
		eBottom_and_side_rough_milling.setRadial_cutting_depth(null, condicoesDeUsinagem.getAe());
		
//		EContour_parallel eContour_parallel = (EContour_parallel)model.createEntityInstance(EContour_parallel.class);
//		eContour_parallel.setOverlap(null, 0.2 * ws.getFerramenta().getDiametroFerramenta());
//		eContour_parallel.setAllow_multiple_passes(null, Boolean.TRUE);
//		eContour_parallel.setRotation_direction(null, ERot_direction.CCW);
//		eContour_parallel.setCutmode(null, ECutmode_type.CONVENTIONAL);
//		eBottom_and_side_rough_milling.setIts_machining_strategy(null, eContour_parallel);
		if(ws.getOperation().getMachiningStrategy() !=null)
		{
			ETwo5d_milling_strategy strategy = createStrategy((Two5DMillingStrategy)bottomAndSideRoughMilling.getMachiningStrategy());
			eBottom_and_side_rough_milling.setIts_machining_strategy(null, strategy);
		}
		eBottom_and_side_rough_milling.setAllowance_side(null, bottomAndSideRoughMilling.getAllowanceSide());
		eBottom_and_side_rough_milling.setAllowance_bottom(null, bottomAndSideRoughMilling.getAllowanceBottom());
		
		return eBottom_and_side_rough_milling; 
	}
	private EBottom_and_side_finish_milling createBottomAndSideFinishOperation(Workingstep ws) throws SdaiException
	{
		BottomAndSideFinishMilling bottomAndSideFinishMilling = (BottomAndSideFinishMilling)ws.getOperation();
		CondicoesDeUsinagem condicoesDeUsinagem = ws.getCondicoesUsinagem();
		
		EBottom_and_side_finish_milling eBottom_and_side_finish_milling = (EBottom_and_side_finish_milling)this.model.createEntityInstance(EBottom_and_side_finish_milling.class);
		eBottom_and_side_finish_milling.setIts_id(null, ws.getOperation().getId());
		eBottom_and_side_finish_milling.setRetract_plane(null, bottomAndSideFinishMilling.getRetractPlane());
		if(ws.getFerramenta().getClass() == EndMill.class)
		{
			eBottom_and_side_finish_milling.setIts_tool(null, this.createEndMill(ws));
		} else if(ws.getFerramenta().getClass() == BallEndMill.class)
		{
			eBottom_and_side_finish_milling.setIts_tool(null, this.createBallEndMillTool(ws));
		} else if(ws.getFerramenta().getClass() == BullnoseEndMill.class)
		{
			eBottom_and_side_finish_milling.setIts_tool(null, this.createBullNoseEndMill(ws));
		} 
		eBottom_and_side_finish_milling.setIts_technology(null, createTechnology(condicoesDeUsinagem.getF(), condicoesDeUsinagem.getN(), condicoesDeUsinagem.getVc()));
		eBottom_and_side_finish_milling.setIts_machine_functions(null, createMachineFunctions(bottomAndSideFinishMilling.isCoolant()));
		eBottom_and_side_finish_milling.setStart_point(null, this.createCartesianPoint("start point", ws.getOperation().getStartPoint()));
		//eBottom_and_side_finish_milling.setOvercut_length(null, 0.0);
		
		EPlunge_toolaxis strategyApproach = (EPlunge_toolaxis)this.model.createEntityInstance(EPlunge_toolaxis.class);
		ArrayList<Double> ratios = new ArrayList<Double>();
		ratios.add(ws.getFeature().getPosition().getAxis().get(0));
		ratios.add(ws.getFeature().getPosition().getAxis().get(1));
		ratios.add(ws.getFeature().getPosition().getAxis().get(2));
		
		EPlunge_toolaxis strategyRetract = (EPlunge_toolaxis)this.model.createEntityInstance(EPlunge_toolaxis.class);
		ArrayList<Double> ratios1 = new ArrayList<Double>();
		ratios1.add(ws.getFeature().getPosition().getAxis().get(0));
		ratios1.add(ws.getFeature().getPosition().getAxis().get(1));
		ratios1.add(-ws.getFeature().getPosition().getAxis().get(2));
		
		EDirection approach = createDirection("approach strategy direction", ratios);
		strategyApproach.setTool_orientation(null, approach);
		
					
		EDirection retract = createDirection("retract strategy direction", ratios1);
		strategyRetract.setTool_orientation(null, retract);
		eBottom_and_side_finish_milling.setApproach(null, strategyApproach);
		eBottom_and_side_finish_milling.setRetract(null, strategyRetract);
		eBottom_and_side_finish_milling.setAxial_cutting_depth(null, condicoesDeUsinagem.getAp());
		eBottom_and_side_finish_milling.setRadial_cutting_depth(null, condicoesDeUsinagem.getAe());
		EContour_parallel eContour_parallel = (EContour_parallel)model.createEntityInstance(EContour_parallel.class);
		eContour_parallel.setOverlap(null, 0.2 * ws.getFerramenta().getDiametroFerramenta());
		eContour_parallel.setAllow_multiple_passes(null, Boolean.TRUE);
		eContour_parallel.setRotation_direction(null, ERot_direction.CCW);
		eContour_parallel.setCutmode(null, ECutmode_type.CONVENTIONAL);
		eBottom_and_side_finish_milling.setIts_machining_strategy(null, eContour_parallel);
		
		eBottom_and_side_finish_milling.setAllowance_side(null, bottomAndSideFinishMilling.getAllowanceSide());
		eBottom_and_side_finish_milling.setAllowance_bottom(null, bottomAndSideFinishMilling.getAllowanceBottom());
		
		return eBottom_and_side_finish_milling;
	}
	private EFreeform_operation createFreeFormOperation(Workingstep ws) throws SdaiException
	{
		EFreeform_operation eFree_form_operation = (EFreeform_operation)this.model.createEntityInstance(EFreeform_operation.class);
		CondicoesDeUsinagem cu = ws.getCondicoesUsinagem();
		FreeformOperation ffo = (FreeformOperation)ws.getOperation();
		eFree_form_operation.setIts_id(null, ffo.getId());
		eFree_form_operation.setRetract_plane(null, ffo.getRetractPlane());
		
		if(ws.getFerramenta().getClass() == BallEndMill.class)
		{
			eFree_form_operation.setIts_tool(null, createBallEndMillTool(ws));
		} 
		else if(ws.getFerramenta().getClass() == BullnoseEndMill.class)
		{
			eFree_form_operation.setIts_tool(null, this.createBullNoseEndMill(ws));
		} 
		else if(ws.getFerramenta().getClass() == FaceMill.class)
		{
			eFree_form_operation.setIts_tool(null, this.createFaceMill(ws));
		} 
		else if(ws.getFerramenta().getClass() == EndMill.class)
		{
			eFree_form_operation.setIts_tool(null, this.createEndMill(ws));
		}
		eFree_form_operation.setIts_technology(null, this.createTechnology(cu.getF(), cu.getN(), cu.getVc()));
		eFree_form_operation.setIts_machine_functions(null, this.createMachineFunctions(ffo.isCoolant()));
		
		EPlunge_toolaxis strategyApproach = (EPlunge_toolaxis)this.model.createEntityInstance(EPlunge_toolaxis.class);
		ArrayList<Double> ratios = new ArrayList<Double>();
		ratios.add(ws.getFeature().getPosition().getAxis().get(0));
		ratios.add(ws.getFeature().getPosition().getAxis().get(1));
		ratios.add(ws.getFeature().getPosition().getAxis().get(2));
		
		EPlunge_toolaxis strategyRetract = (EPlunge_toolaxis)this.model.createEntityInstance(EPlunge_toolaxis.class);
		ArrayList<Double> ratios1 = new ArrayList<Double>();
		ratios1.add(ws.getFeature().getPosition().getAxis().get(0));
		ratios1.add(ws.getFeature().getPosition().getAxis().get(1));
		ratios1.add(-ws.getFeature().getPosition().getAxis().get(2));
		
		EDirection approach = createDirection("approach strategy direction", ratios);
		strategyApproach.setTool_orientation(null, approach);
		EDirection retract = createDirection("retract strategy direction", ratios1);
		strategyRetract.setTool_orientation(null, retract);
		eFree_form_operation.setApproach(null, strategyApproach);
		eFree_form_operation.setRetract(null, strategyRetract);
		eFree_form_operation.setIts_machining_strategy(null, this.createFreeFormStategy(ws));
		return eFree_form_operation;
	}
	private EFreeform_strategy createFreeFormStategy(Workingstep ws) throws SdaiException
	{
		EUv_strategy strategy = (EUv_strategy)this.model.createEntityInstance(EUv_strategy.class);
		strategy.setPathmode(null, EPathmode_type.ZIGZAG);
		strategy.setCutmode(null, ECutmode_type.CONVENTIONAL);
		ETolerances tolerances = (ETolerances)model.createEntityInstance(ETolerances.class);
		tolerances.setChordal_tolerance(null, .05); /** ARRUMAR DEPOIS **/
		tolerances.setScallop_height(null, .05); /** ARRUMAR DEPOIS **/
		strategy.setIts_milling_tolerances(null, tolerances);
		ArrayList<Double> uRatios = new ArrayList<Double>();
		uRatios.add(1.0);
		uRatios.add(0.0);
		uRatios.add(0.0);
		ArrayList<Double> vRatios = new ArrayList<Double>();
		vRatios.add(0.0);
		vRatios.add(1.0);
		vRatios.add(0.0);
		strategy.setForward_direction(null, createDirection("u direction", uRatios));
		strategy.setSideward_direction(null, createDirection("v direction", vRatios));
		return strategy;
	}
//	private EMachining_operation createOperations(Feature featureType, int finishingType, String name, double retractPlane, double ap, double ae, double vc) throws SdaiException
//	{
//		if (finishingType == Workingstep.DESBASTE)
//		{
//			if (featureType.getClass() == Ranhura.class||featureType.getClass() == Cavidade.class || featureType.getClass() == Degrau.class) 
//			{
//				EBottom_and_side_rough_milling eBottom_and_side_rough_milling =(EBottom_and_side_rough_milling)model.createEntityInstance(EBottom_and_side_rough_milling.class);
//				eBottom_and_side_rough_milling.setIts_id(null, name);
//				eBottom_and_side_rough_milling.setRetract_plane(null, retractPlane);
//				eBottom_and_side_rough_milling.setIts_tool(null, createMachiningTool("tool",featureType,  toolLength, finishingType));
//				eBottom_and_side_rough_milling.setIts_technology(null, createTechnology(feedRate, spindleRotation, vc));
//				eBottom_and_side_rough_milling.setIts_machine_functions(null, createMachineFunctions(Boolean.FALSE));
//				eBottom_and_side_rough_milling.setOvercut_length(null, 0.00);
//				EPlunge_toolaxis strategyApproach = (EPlunge_toolaxis)model.createEntityInstance(EPlunge_toolaxis.class);
//				ArrayList<Double> ratios = new ArrayList<Double>();
//				ratios.add(featureAxis.get(0));
//				ratios.add(featureAxis.get(1));
//				ratios.add(featureAxis.get(2));
//				
//				EPlunge_toolaxis strategyRetract = (EPlunge_toolaxis)model.createEntityInstance(EPlunge_toolaxis.class);
//				ArrayList<Double> ratios1 = new ArrayList<Double>();
//				ratios1.add(featureAxis.get(0));
//				ratios1.add(featureAxis.get(1));
//				ratios1.add(-featureAxis.get(2));
//				
//				EDirection approach = createDirection("approach strategy direction", ratios);
//				strategyApproach.setTool_orientation(null, approach);
//				
//				EDirection retract = createDirection("retract strategy direction", ratios1);
//				strategyRetract.setTool_orientation(null, retract);
//				eBottom_and_side_rough_milling.setApproach(null, strategyApproach);
//				eBottom_and_side_rough_milling.setRetract(null, strategyRetract);
//				eBottom_and_side_rough_milling.setAxial_cutting_depth(null, ap);
//				eBottom_and_side_rough_milling.setRadial_cutting_depth(null, ae);
//				EContour_parallel eContour_parallel = (EContour_parallel)model.createEntityInstance(EContour_parallel.class);
//				eContour_parallel.setOverlap(null, 0.2 * toolDiameter);
//				eContour_parallel.setAllow_multiple_passes(null, Boolean.TRUE);
//				eContour_parallel.setRotation_direction(null, ERot_direction.CCW);
//				eContour_parallel.setCutmode(null, ECutmode_type.CONVENTIONAL);
//				eBottom_and_side_rough_milling.setIts_machining_strategy(null, eContour_parallel);
//				eBottom_and_side_rough_milling.setAllowance_side(null, 0.5);
//				eBottom_and_side_rough_milling.setAllowance_bottom(null, 0.5);
//				
//				return eBottom_and_side_rough_milling;
//			}
//			else if (featureType.getClass() == FuroBasePlana.class)
//			{
//				EDrilling eDrilling = (EDrilling)this.model.createEntityInstance(EDrilling.class);
//				eDrilling.setIts_id(null, name);
//				eDrilling.setRetract_plane(null, retractPlane);
//				eDrilling.setIts_tool(null, createMachiningTool("Twist drill", featureType, toolLength, finishingType));
//				eDrilling.setIts_technology(null, createTechnology(feedRate, spindleRotation, vc));
//				eDrilling.setIts_machine_functions(null, createMachineFunctions(Boolean.FALSE));
//				EDrilling_type_strategy eDrilling_type_strategy = (EDrilling_type_strategy)this.model.createEntityInstance(EDrilling_type_strategy.class);
//				eDrilling.setIts_machining_strategy(null, eDrilling_type_strategy);
//				return eDrilling;
//			}
//		} else if (finishingType == Workingstep.ACABAMENTO)
//		{
//			if (featureType.getClass() == Ranhura.class||featureType.getClass() == Cavidade.class || featureType.getClass() == Degrau.class) 
//			{
//				EBottom_and_side_finish_milling eBottom_and_side_finish_milling = (EBottom_and_side_finish_milling)this.model.createEntityInstance(EBottom_and_side_finish_milling.class);
//				eBottom_and_side_finish_milling.setIts_id(null, name);
//				eBottom_and_side_finish_milling.setRetract_plane(null, retractPlane);
//				eBottom_and_side_finish_milling.setIts_tool(null, createMachiningTool("tool", featureType, toolLength, finishingType));
//				eBottom_and_side_finish_milling.setIts_technology(null, createTechnology(feedRate, spindleRotation, vc));
//				eBottom_and_side_finish_milling.setIts_machine_functions(null, createMachineFunctions(Boolean.FALSE));
//				eBottom_and_side_finish_milling.setOvercut_length(null, 0.00);
//				
//				EPlunge_toolaxis strategyApproach = (EPlunge_toolaxis)this.model.createEntityInstance(EPlunge_toolaxis.class);
//				ArrayList<Double> ratios = new ArrayList<Double>();
//				ratios.add(featureAxis.get(0));
//				ratios.add(featureAxis.get(1));
//				ratios.add(featureAxis.get(2));
//				
//				EPlunge_toolaxis strategyRetract = (EPlunge_toolaxis)this.model.createEntityInstance(EPlunge_toolaxis.class);
//				ArrayList<Double> ratios1 = new ArrayList<Double>();
//				ratios1.add(featureAxis.get(0));
//				ratios1.add(featureAxis.get(1));
//				ratios1.add(-featureAxis.get(2));
//				
//				EDirection approach = createDirection("approach strategy direction", ratios);
//				strategyApproach.setTool_orientation(null, approach);
//				
//				EDirection retract = createDirection("retract strategy direction", ratios1);
//				strategyRetract.setTool_orientation(null, retract);
//				eBottom_and_side_finish_milling.setApproach(null, strategyApproach);
//				eBottom_and_side_finish_milling.setRetract(null, strategyRetract);
//				eBottom_and_side_finish_milling.setAxial_cutting_depth(null, ap);
//				eBottom_and_side_finish_milling.setRadial_cutting_depth(null, ae);
//				EContour_parallel eContour_parallel = (EContour_parallel)model.createEntityInstance(EContour_parallel.class);
//				eContour_parallel.setOverlap(null, 0.2 * toolDiameter);
//				eContour_parallel.setAllow_multiple_passes(null, Boolean.TRUE);
//				eContour_parallel.setRotation_direction(null, ERot_direction.CCW);
//				eContour_parallel.setCutmode(null, ECutmode_type.CONVENTIONAL);
//				eBottom_and_side_finish_milling.setIts_machining_strategy(null, eContour_parallel);
//				eBottom_and_side_finish_milling.setAllowance_side(null, 0.0);
//				eBottom_and_side_finish_milling.setAllowance_bottom(null, 0.0);
//				return eBottom_and_side_finish_milling;
//			}
//			else if (featureType.getClass() == FuroBasePlana.class)
//			{
//				EReaming eReaming = (EReaming)this.model.createEntityInstance(EReaming.class);
//				eReaming.setIts_id(null, name);
//				eReaming.setRetract_plane(null, retractPlane);
//				eReaming.setIts_tool(null, createMachiningTool("Reamer", featureType, toolLength, finishingType));
//				eReaming.setIts_technology(null, createTechnology(feedRate, spindleRotation, vc));
//				eReaming.setIts_machine_functions(null, createMachineFunctions(Boolean.FALSE));
////				eReaming.setPrevious_diameter(null, previusDiameter);
//				EDrilling_type_strategy eDrilling_type_strategy = (EDrilling_type_strategy)this.model.createEntityInstance(EDrilling_type_strategy.class);
//				eReaming.setIts_machining_strategy(null, eDrilling_type_strategy);
//				return eReaming;
//			}
//		}
//		return null;
//	}
	private ERectangular_closed_profile createRectangularClosedProfile(Feature feature) throws SdaiException
	{
		ERectangular_closed_profile eRectangular_closed_profile = (ERectangular_closed_profile)this.model.createEntityInstance(ERectangular_closed_profile.class);
		eRectangular_closed_profile.setPlacement(null, this.createAxis2Placement3D(feature.getNome() + " placement", feature.getPosition().getCoordinates(), feature.getPosition().getAxis(), feature.getPosition().getRefDirection()));
		
		if(feature.getClass() == Cavidade.class)
		{
			Cavidade cavidade = (Cavidade)feature;
			eRectangular_closed_profile.setProfile_length(null, createTolerancedLengthMeasure(cavidade.getComprimento(), cavidade.getTolerancia()));
			eRectangular_closed_profile.setProfile_width(null, createTolerancedLengthMeasure(cavidade.getLargura(), cavidade.getTolerancia()));
		} else if (feature.getClass() == CavidadeFundoArredondado.class)
		{
			CavidadeFundoArredondado cavidade = (CavidadeFundoArredondado)feature;
			eRectangular_closed_profile.setProfile_length(null, createTolerancedLengthMeasure(cavidade.getComprimento(), cavidade.getTolerancia()));
			eRectangular_closed_profile.setProfile_width(null, createTolerancedLengthMeasure(cavidade.getLargura(), cavidade.getTolerancia()));
		}
		return eRectangular_closed_profile;
	}
	private ERectangular_closed_profile createRectangularClosedProfile(EAxis2_placement_3d placement, double length, double width) throws SdaiException
	{
		ERectangular_closed_profile eRectangular_closed_profile = (ERectangular_closed_profile)this.model.createEntityInstance(ERectangular_closed_profile.class);
		eRectangular_closed_profile.setPlacement(null, placement);
		eRectangular_closed_profile.setProfile_length(null, createTolerancedLengthMeasure(length, pocketTolerance));
		eRectangular_closed_profile.setProfile_width(null, createTolerancedLengthMeasure(width, pocketTolerance));
		
		return eRectangular_closed_profile;
	}
	private ESquare_u_profile createSquareUProfile(EAxis2_placement_3d placement, double width) throws SdaiException
	{
		ESquare_u_profile eSquare_u_profile = (ESquare_u_profile)model.createEntityInstance(ESquare_u_profile.class);
		eSquare_u_profile.setPlacement(null, placement);
		eSquare_u_profile.setWidth(null, createTolerancedLengthMeasure(width, this.globalTolerance));
		return eSquare_u_profile;
	}
	private ESquare_u_profile createSquareUProfile(Feature feature) throws SdaiException
	{
		ESquare_u_profile eSquare_u_profile = (ESquare_u_profile)this.model.createEntityInstance(ESquare_u_profile.class);
		ArrayList<Double> profileAxis = new ArrayList<Double>();
		ArrayList<Double> profileRefDirection = new ArrayList<Double>();
		Point3d profilePlacement = null;
		double width, firstRadius, firstAngle, secondRadius, secondAngle;
		if(feature.getClass() == Ranhura.class)
		{
			Ranhura ranhura = (Ranhura)feature;
			width = ranhura.getLargura();
			eSquare_u_profile.setWidth(null, this.createTolerancedLengthMeasure(width, ranhura.getTolerancia()));
			if(ranhura.getEixo() == Ranhura.HORIZONTAL)
			{
				profilePlacement = new Point3d(0, 0, -ranhura.getProfundidade());
				profileAxis.add(0.0);
				profileAxis.add(0.0);
				profileAxis.add(1.0);
				
				profileRefDirection.add(0.0);
				profileRefDirection.add(-1.0);
				profileRefDirection.add(0.0);
			} else if(ranhura.getEixo() == Ranhura.VERTICAL)
			{
				profilePlacement = new Point3d(0, 0, -ranhura.getProfundidade());
				profileAxis.add(0.0);
				profileAxis.add(0.0);
				profileAxis.add(1.0);
				
				profileRefDirection.add(1.0);
				profileRefDirection.add(0.0);
				profileRefDirection.add(0.0);
			} 
		}else if(feature.getClass() == RanhuraPerfilQuadradoU.class)
		{
			RanhuraPerfilQuadradoU ranhura = (RanhuraPerfilQuadradoU)feature;
			width = ranhura.getLargura2();
			firstRadius = secondRadius = ranhura.getRaio();
			firstAngle = secondAngle = ranhura.getAngulo() * Math.PI/180;
			eSquare_u_profile.setWidth(null, this.createTolerancedLengthMeasure(width, ranhura.getTolerancia()));
			eSquare_u_profile.setFirst_radius(null, this.createTolerancedLengthMeasure(firstRadius, ranhura.getTolerancia()));
			eSquare_u_profile.setSecond_radius(null, this.createTolerancedLengthMeasure(secondRadius, ranhura.getTolerancia()));
			eSquare_u_profile.setFirst_angle(null, firstAngle);
			eSquare_u_profile.setSecond_angle(null, secondAngle);
			
			if(ranhura.getEixo() == Ranhura.HORIZONTAL)
			{
				profilePlacement = new Point3d(0, 0, -ranhura.getProfundidade());
				profileAxis.add(0.0);
				profileAxis.add(0.0);
				profileAxis.add(1.0);
				
				profileRefDirection.add(0.0);
				profileRefDirection.add(-1.0);
				profileRefDirection.add(0.0);
			} else if(ranhura.getEixo() == Ranhura.VERTICAL)
			{
				profilePlacement = new Point3d(0, 0, -ranhura.getProfundidade());
				profileAxis.add(0.0);
				profileAxis.add(0.0);
				profileAxis.add(1.0);
				
				profileRefDirection.add(1.0);
				profileRefDirection.add(0.0);
				profileRefDirection.add(0.0);
			}
		} else if(feature.getClass() == Degrau.class)
		{
			Degrau degrau = (Degrau)feature;
			width = degrau.getLargura();
			eSquare_u_profile.setWidth(null, this.createTolerancedLengthMeasure(width, degrau.getTolerancia()));
		}
		// profile placement (the profile coordinate system coincide with the feature coordinate system.
				
		eSquare_u_profile.setPlacement(null, this.createAxis2Placement3D(null, profilePlacement, profileAxis, profileRefDirection));
		
		return eSquare_u_profile;
	}
	private EPartial_circular_profile createPartialCircularProfile(RanhuraPerfilCircularParcial ranhura) throws SdaiException
	{
		EPartial_circular_profile ePartial_circular_profile = (EPartial_circular_profile)this.model.createEntityInstance(EPartial_circular_profile.class);
		Point3d locationProfile = new Point3d(0, 0, ranhura.getDz());
		ArrayList<Double> axisProfile = new ArrayList<Double>();
		ArrayList<Double> refDirectionProfile = new ArrayList<Double>();
		double a = Math.asin(ranhura.getDz() / ranhura.getRaio());
		double d = ranhura.getRaio() * Math.cos(a);

		if (ranhura.getEixo() == Ranhura.HORIZONTAL)
		{
			axisProfile.add(1.0);
			axisProfile.add(0.0);
			axisProfile.add(0.0);
			refDirectionProfile.add(0.0);
			refDirectionProfile.add(-d/ranhura.getRaio());
			refDirectionProfile.add(-ranhura.getDz() / ranhura.getRaio());
		} else if(ranhura.getEixo() == Ranhura.VERTICAL)
		{
			axisProfile.add(0.0);
			axisProfile.add(1.0);
			axisProfile.add(0.0);
			refDirectionProfile.add(d/ranhura.getRaio());
			refDirectionProfile.add(0.0);
			refDirectionProfile.add(ranhura.getDz() / ranhura.getRaio() * -1);
		}
		ePartial_circular_profile.setPlacement(null, this.createAxis2Placement3D("partial circular profile location", locationProfile, axisProfile, refDirectionProfile));
		ePartial_circular_profile.setRadius(null, this.createTolerancedLengthMeasure(ranhura.getRaio(), ranhura.getTolerancia()));
		ePartial_circular_profile.setSweep_angle(null, ranhura.getAngulo() * Math.PI / 180);
		System.out.println("ranhura angulo --> " + ranhura.getAngulo());
		return ePartial_circular_profile;
	}
	private ERounded_u_profile createRoundedUProfile(RanhuraPerfilRoundedU ranhura) throws SdaiException
	{
		ERounded_u_profile eRounded_u_profile = (ERounded_u_profile)this.model.createEntityInstance(ERounded_u_profile.class);
		Point3d locationProfile = new Point3d(0, 0, -ranhura.getProfundidade());
		ArrayList<Double> axisProfile = new ArrayList<Double>();
		ArrayList<Double> refDirectionProfile = new ArrayList<Double>();
		if (ranhura.getEixo() == Ranhura.HORIZONTAL)
		{
			axisProfile.add(0.0);
			axisProfile.add(0.0);
			axisProfile.add(1.0);
			refDirectionProfile.add(0.0);
			refDirectionProfile.add(-1.0);
			refDirectionProfile.add(0.0);
		} else if (ranhura.getEixo() == Ranhura.VERTICAL)
		{
			axisProfile.add(0.0);
			axisProfile.add(0.0);
			axisProfile.add(1.0);
			refDirectionProfile.add(1.0);
			refDirectionProfile.add(0.0);
			refDirectionProfile.add(0.0);
		}
		
		eRounded_u_profile.setPlacement(null, this.createAxis2Placement3D("rounded u profile location", locationProfile, axisProfile, refDirectionProfile));
		eRounded_u_profile.setWidth(null, this.createTolerancedLengthMeasure(ranhura.getLargura(), ranhura.getTolerancia()));
		return eRounded_u_profile;
	}
	private EVee_profile createVeeProfile(RanhuraPerfilVee ranhura) throws SdaiException
	{
		EVee_profile eVee_profile = (EVee_profile)this.model.createEntityInstance(EVee_profile.class);
		Point3d locationProfile = new Point3d(0, 0, -ranhura.getProfundidade());
		ArrayList<Double> axisProfile = new ArrayList<Double>();
		ArrayList<Double> refDirectionProfile = new ArrayList<Double>();
		if(ranhura.getEixo() == Ranhura.HORIZONTAL)
		{
			axisProfile.add(0.0);
			axisProfile.add(0.0);
			axisProfile.add(1.0);
			refDirectionProfile.add(0.0);
			refDirectionProfile.add(-1.0);
			refDirectionProfile.add(0.0);
		} else if(ranhura.getEixo() == Ranhura.VERTICAL)
		{
			axisProfile.add(0.0);
			axisProfile.add(0.0);
			axisProfile.add(1.0);
			refDirectionProfile.add(1.0);
			refDirectionProfile.add(0.0);
			refDirectionProfile.add(0.0);
		}
		eVee_profile.setPlacement(null, this.createAxis2Placement3D("vee profile location", locationProfile, axisProfile, refDirectionProfile));
		eVee_profile.setProfile_angle(null, ranhura.getAngulo() * Math.PI / 180);
		eVee_profile.setTilt_angle(null, (180 - ranhura.getAngulo()) * Math.PI / 180 /2);
		eVee_profile.setProfile_radius(null, this.createTolerancedLengthMeasure(ranhura.getRaio(), ranhura.getTolerancia()));
		
		return eVee_profile;
	}
	private EGeneral_profile createBezierProfile(RanhuraPerfilBezier ranhura) throws SdaiException
	{
		EGeneral_profile eGeneral_profile = (EGeneral_profile)this.model.createEntityInstance(EGeneral_profile.class);
		Point3d locationProfile = new Point3d(0, 0, 0);
		ArrayList<Double> axisProfile = new ArrayList<Double>();
		ArrayList<Double> refDirectionProfile = new ArrayList<Double>();
		if (ranhura.getEixo() == Ranhura.HORIZONTAL)
		{
			axisProfile.add(0.0);
			axisProfile.add(0.0);
			axisProfile.add(1.0);
			refDirectionProfile.add(0.0);
			refDirectionProfile.add(1.0);
			refDirectionProfile.add(0.0);	
		} else if (ranhura.getEixo() == Ranhura.VERTICAL)
		{
			axisProfile.add(0.0);
			axisProfile.add(0.0);
			axisProfile.add(1.0);
			refDirectionProfile.add(1.0);
			refDirectionProfile.add(0.0);
			refDirectionProfile.add(0.0);
		}
		
		eGeneral_profile.setPlacement(null, this.createAxis2Placement3D("general profile location", locationProfile, axisProfile, refDirectionProfile));
		EBezier_curve eBezier_curve = (EBezier_curve)this.model.createEntityInstance(EBezier_curve.class);
		eBezier_curve.setName(null, "bezier curve");
		eBezier_curve.setDegree(null, ranhura.getPontosDeControle().length);
		ACartesian_point aCartesian_point = eBezier_curve.createControl_points_list(null);
		
		for(int i = 0; i < ranhura.getPontosDeControle().length; i++)
		{
			ECartesian_point eCartesian_point = (ECartesian_point)this.model.createEntityInstance(ECartesian_point.class); // isto por causa que está em relacao ao sistema de coordenadas da feature
			A_double a_double = eCartesian_point.createCoordinates(null);
			a_double.addByIndex(1, ranhura.getPontosDeControle()[i].x - ranhura.getDeslocamento());
			a_double.addByIndex(2, ranhura.getPontosDeControle()[i].z * 0); 
			a_double.addByIndex(3, ranhura.getPontosDeControle()[i].y - ranhura.Z); // isto por causa que está em relacao ao sistema de coordenadas da feature
			aCartesian_point.addByIndex(i + 1, eCartesian_point);
		}
		eGeneral_profile.setIts_profile(null, eBezier_curve);
		
		eBezier_curve.setCurve_form(null, EB_spline_curve_form.UNSPECIFIED);
		eBezier_curve.setClosed_curve(null, ELogical.FALSE);
		eBezier_curve.setSelf_intersect(null, ELogical.FALSE);
		
		return eGeneral_profile;
	}
	private EToleranced_length_measure createTolerancedLengthMeasure(double theoricalSize, double tolerance) throws SdaiException
	{
		EToleranced_length_measure eToleranced_length_measure = (EToleranced_length_measure)this.model.createEntityInstance(EToleranced_length_measure.class);
		if (tolerance != 0)
		{
			EPlus_minus_value ePlus_minus_value = (EPlus_minus_value)this.model.createEntityInstance(EPlus_minus_value.class);
			ePlus_minus_value.setUpper_limit(null, tolerance);
			ePlus_minus_value.setLower_limit(null, tolerance);
			ePlus_minus_value.setSignificant_digits(null, 4);
			eToleranced_length_measure.setImplicit_tolerance(null, ePlus_minus_value);
		}
		eToleranced_length_measure.setTheoretical_size(null, theoricalSize);
		return eToleranced_length_measure;
	}
	private EToleranced_length_measure createTolerancedLengthMeasure(double theoricalSize) throws SdaiException
	{
		EToleranced_length_measure eToleranced_length_measure = (EToleranced_length_measure)this.model.createEntityInstance(EToleranced_length_measure.class);
		eToleranced_length_measure.setTheoretical_size(null, theoricalSize);
		return eToleranced_length_measure;
	}
	private EMilling_cutting_tool createTwistDrillTool(Workingstep ws) throws SdaiException
	{
		TwistDrill broca = (TwistDrill)ws.getFerramenta();
		EMilling_cutting_tool tool = (EMilling_cutting_tool)this.model.createEntityInstance(EMilling_cutting_tool.class);
		
		ETwist_drill eTwist_drill = (ETwist_drill)this.model.createEntityInstance(ETwist_drill.class);
		EMilling_tool_dimension eTool_dimension = this.createToolDimension(broca);
		eTwist_drill.setDimension(null, eTool_dimension);
		eTwist_drill.setNumber_of_teeth(null, 2);
		eTwist_drill.setHand_of_cut(null, broca.getHandOfCut());
		eTwist_drill.setPilot_length(null, broca.getProfundidadeMaxima());
		tool.setIts_tool_body(null, eTwist_drill);
		tool.setIts_id(null, broca.getName());
		ACutting_component aCutting_component = tool.createIts_cutting_edge(null);
		ECutting_component eCutting_component = (ECutting_component)this.model.createEntityInstance(ECutting_component.class);
		eCutting_component.setTool_offset_length(null, broca.getOffsetLength());
		EMaterial eMaterial = (EMaterial)this.model.createEntityInstance(EMaterial.class);
		eMaterial.setStandard_identifier(null, broca.getMaterial());
		eMaterial.setMaterial_identifier(null, "CARBIDE");
		eMaterial.createMaterial_property(null);
		eCutting_component.setIts_material(null, eMaterial);
		aCutting_component.addByIndex(1, eCutting_component);
		return tool;
	}
	private EMilling_cutting_tool createReamerTool(Workingstep ws) throws SdaiException
	{
		Reamer reamer = (Reamer)ws.getFerramenta();
		EMilling_cutting_tool tool = (EMilling_cutting_tool)this.model.createEntityInstance(EMilling_cutting_tool.class);
		EReamer eReamer = (EReamer)this.model.createEntityInstance(EReamer.class);
		EMilling_tool_dimension eTool_dimension = createToolDimension(reamer);
		eReamer.setDimension(null, eTool_dimension);
		eReamer.setNumber_of_teeth(null, reamer.getNumberOfTeeth());
		eReamer.setHand_of_cut(null, reamer.getHandOfCut());
		eReamer.setPilot_length(null, reamer.getProfundidadeMaxima());
		tool.setIts_tool_body(null, eReamer);
		tool.setIts_id(null, reamer.getName());
		ACutting_component aCutting_component = tool.createIts_cutting_edge(null);
		ECutting_component eCutting_component = (ECutting_component)this.model.createEntityInstance(ECutting_component.class);
		EMaterial eMaterial = (EMaterial)this.model.createEntityInstance(EMaterial.class);
		eMaterial.setStandard_identifier(null,reamer.getMaterial());
		eMaterial.setMaterial_identifier(null, "CARBIDE");
		eMaterial.createMaterial_property(null);
		eCutting_component.setIts_material(null, eMaterial);
		eCutting_component.setTool_offset_length(null, reamer.getOffsetLength());
		aCutting_component.addByIndex(1, eCutting_component);
		return tool;
	}
	private EMilling_cutting_tool createBoringTool(Workingstep ws) throws SdaiException
	{
		BoringTool borer = (BoringTool)ws.getFerramenta();
		EMilling_cutting_tool tool = (EMilling_cutting_tool)this.model.createEntityInstance(EMilling_cutting_tool.class);
		EBoring_tool eBoring_tool = (EBoring_tool)this.model.createEntityInstance(EBoring_tool.class);
		EMilling_tool_dimension eTool_dimension = this.createToolDimension(borer);
		eBoring_tool.setDimension(null, eTool_dimension);
		eBoring_tool.setNumber_of_teeth(null, borer.getNumberOfTeeth());
		eBoring_tool.setHand_of_cut(null, borer.getHandOfCut());
		eBoring_tool.setPilot_length(null, borer.getProfundidadeMaxima());
		eBoring_tool.setCoolant_through_tool(null, true);
		eBoring_tool.setRetract_movement_forbidden(null, false);
		tool.setIts_tool_body(null, eBoring_tool);
		tool.setIts_id(null, borer.getName());
		ACutting_component aCutting_component = tool.createIts_cutting_edge(null);
		ECutting_component eCutting_component = (ECutting_component)this.model.createEntityInstance(ECutting_component.class);
		EMaterial eMaterial = (EMaterial)this.model.createEntityInstance(EMaterial.class);
		eMaterial.setStandard_identifier(null, borer.getMaterial());
		eMaterial.setMaterial_identifier(null, "CARBIDE");
		eMaterial.createMaterial_property(null);
		eCutting_component.setIts_material(null, eMaterial);
		eCutting_component.setTool_offset_length(null, borer.getOffsetLength());
		aCutting_component.addByIndex(1, eCutting_component);
		return tool;
	}
	private EMilling_cutting_tool createCenterDrillTool(Workingstep ws) throws SdaiException
	{
		CenterDrill centerDrill = (CenterDrill)ws.getFerramenta();
		EMilling_cutting_tool tool = (EMilling_cutting_tool)this.model.createEntityInstance(EMilling_cutting_tool.class);
		ECenterdrill eCenter_drill = (ECenterdrill)this.model.createEntityInstance(ECenterdrill.class);
		EMilling_tool_dimension eTool_dimension = this.createToolDimension(centerDrill);
		eCenter_drill.setDimension(null, eTool_dimension);
		eCenter_drill.setNumber_of_teeth(null, centerDrill.getNumberOfTeeth());
		eCenter_drill.setHand_of_cut(null, centerDrill.getHandOfCut());
		eCenter_drill.setPilot_length(null, centerDrill.getProfundidadeMaxima());
		tool.setIts_tool_body(null, eCenter_drill);
		tool.setIts_id(null, centerDrill.getName());
		ACutting_component aCutting_component = tool.createIts_cutting_edge(null);
		ECutting_component eCutting_component = (ECutting_component)this.model.createEntityInstance(ECutting_component.class);
		EMaterial eMaterial = (EMaterial)this.model.createEntityInstance(EMaterial.class);
		eMaterial.setStandard_identifier(null, centerDrill.getMaterial());
		eMaterial.setMaterial_identifier(null, "CARBIDE");
		eMaterial.createMaterial_property(null);
		eCutting_component.setIts_material(null, eMaterial);
		eCutting_component.setTool_offset_length(null, centerDrill.getOffsetLength());
		aCutting_component.addByIndex(1, eCutting_component);
		return tool;
	}
	private EMilling_cutting_tool createFaceMill(Workingstep ws) throws SdaiException
	{
		FaceMill faceMill = (FaceMill)ws.getFerramenta();
		EMilling_cutting_tool tool = (EMilling_cutting_tool)this.model.createEntityInstance(EMilling_cutting_tool.class);
		EFacemill eFacemill = (EFacemill)this.model.createEntityInstance(EFacemill.class);
		EMilling_tool_dimension eTool_dimension = createToolDimension(faceMill);
		eFacemill.setDimension(null, eTool_dimension);
		eFacemill.setNumber_of_teeth(null, faceMill.getNumberOfTeeth());
		eFacemill.setHand_of_cut(null, faceMill.getHandOfCut());
		eFacemill.setPilot_length(null, faceMill.getProfundidadeMaxima());
		tool.setIts_tool_body(null, eFacemill);
		tool.setIts_id(null, faceMill.getName());
		ACutting_component aCutting_component = tool.createIts_cutting_edge(null);
		ECutting_component eCutting_component = (ECutting_component)model.createEntityInstance(ECutting_component.class);
		EMaterial eMaterial = (EMaterial)this.model.createEntityInstance(EMaterial.class);
		eMaterial.setStandard_identifier(null, faceMill.getMaterial());
		eMaterial.setMaterial_identifier(null, "CARBIDE");
		eMaterial.createMaterial_property(null);
		eCutting_component.setIts_material(null, eMaterial);
		eCutting_component.setTool_offset_length(null, faceMill.getOffsetLength());
		aCutting_component.addByIndex(1, eCutting_component);
		return tool;
	}
	private EMilling_cutting_tool createEndMill(Workingstep ws) throws SdaiException
	{
		EndMill endMill = (EndMill)ws.getFerramenta();
		EMilling_cutting_tool tool = (EMilling_cutting_tool)this.model.createEntityInstance(EMilling_cutting_tool.class);
		EEndmill eEndmill = (EEndmill)this.model.createEntityInstance(EEndmill.class);
		EMilling_tool_dimension eTool_dimension = createToolDimension(endMill);
		eEndmill.setDimension(null, eTool_dimension);
		eEndmill.setNumber_of_teeth(null, endMill.getNumberOfTeeth());
		eEndmill.setHand_of_cut(null, endMill.getHandOfCut());
		eEndmill.setPilot_length(null, endMill.getProfundidadeMaxima());
		tool.setIts_tool_body(null, eEndmill);
		tool.setIts_id(null, endMill.getName());
		ACutting_component aCutting_component = tool.createIts_cutting_edge(null);
		ECutting_component eCutting_component = (ECutting_component)model.createEntityInstance(ECutting_component.class);
		eCutting_component.setTool_offset_length(null, endMill.getOffsetLength());
		EMaterial eMaterial = (EMaterial)this.model.createEntityInstance(EMaterial.class);
		eMaterial.setStandard_identifier(null, endMill.getMaterial());
		eMaterial.setMaterial_identifier(null, "CARBIDE");
		eMaterial.createMaterial_property(null);
		eCutting_component.setIts_material(null, eMaterial);
		aCutting_component.addByIndex(1, eCutting_component);
		return tool;
	}
	private EMilling_cutting_tool createBallEndMillTool(Workingstep ws) throws SdaiException
	{
		BallEndMill ballEndMill = (BallEndMill)ws.getFerramenta();
		EMilling_cutting_tool tool = (EMilling_cutting_tool)this.model.createEntityInstance(EMilling_cutting_tool.class);
		EBall_endmill eBall_endmill = (EBall_endmill)this.model.createEntityInstance(EBall_endmill.class);
		EMilling_tool_dimension eTool_dimension = this.createToolDimension(ballEndMill);
		eBall_endmill.setDimension(null, eTool_dimension);
		eBall_endmill.setNumber_of_teeth(null, ballEndMill.getNumberOfTeeth());
		eBall_endmill.setHand_of_cut(null, ballEndMill.getHandOfCut());
		eBall_endmill.setPilot_length(null, ballEndMill.getProfundidadeMaxima());
		tool.setIts_tool_body(null, eBall_endmill);
		tool.setIts_id(null, ballEndMill.getName());
		ACutting_component aCutting_component = tool.createIts_cutting_edge(null);
		ECutting_component eCutting_component = (ECutting_component)this.model.createEntityInstance(ECutting_component.class);
		eCutting_component.setTool_offset_length(null, ballEndMill.getOffsetLength());
		EMaterial eMaterial = (EMaterial)this.model.createEntityInstance(EMaterial.class);
		eMaterial.setStandard_identifier(null, ballEndMill.getMaterial());
		eMaterial.setMaterial_identifier(null, "CARBIDE");
		eMaterial.createMaterial_property(null);
		eCutting_component.setIts_material(null, eMaterial);
		aCutting_component.addByIndex(1, eCutting_component);
		return tool;
	}
	private EMilling_cutting_tool createBullNoseEndMill(Workingstep ws) throws SdaiException
	{
		BullnoseEndMill bullnoseEndMill = (BullnoseEndMill)ws.getFerramenta();
		EMilling_cutting_tool tool = (EMilling_cutting_tool)this.model.createEntityInstance(EMilling_cutting_tool.class);
		EBullnose_endmill eBullnose_endmill = (EBullnose_endmill)this.model.createEntityInstance(EBullnose_endmill.class);
		EMilling_tool_dimension eTool_dimension = this.createToolDimension(bullnoseEndMill);
		eBullnose_endmill.setDimension(null, eTool_dimension);
		eBullnose_endmill.setNumber_of_teeth(null, bullnoseEndMill.getNumberOfTeeth());
		eBullnose_endmill.setHand_of_cut(null, bullnoseEndMill.getHandOfCut());
		eBullnose_endmill.setPilot_length(null, bullnoseEndMill.getProfundidadeMaxima());
		tool.setIts_tool_body(null, eBullnose_endmill);
		tool.setIts_id(null, bullnoseEndMill.getName());
		ACutting_component aCutting_component = tool.createIts_cutting_edge(null);
		ECutting_component eCutting_component = (ECutting_component)this.model.createEntityInstance(ECutting_component.class);
		eCutting_component.setTool_offset_length(null, bullnoseEndMill.getOffsetLength());
		EMaterial eMaterial = (EMaterial)this.model.createEntityInstance(EMaterial.class);
		eMaterial.setStandard_identifier(null, bullnoseEndMill.getMaterial());
		eMaterial.setMaterial_identifier(null, "CARBIDE");
		eMaterial.createMaterial_property(null);
		eCutting_component.setIts_material(null, eMaterial);
		aCutting_component.addByIndex(1, eCutting_component);
		return tool;
	}
	private EMachining_tool createMachiningTool(String id, Feature featureType, double offsetLength, int finishingType) throws SdaiException
	{
		EMilling_cutting_tool tool = (EMilling_cutting_tool)model.createEntityInstance(EMilling_cutting_tool.class);
		if(finishingType == Workingstep.DESBASTE)
		{
			if(featureType.getClass() == Ranhura.class ||featureType.getClass() == Degrau.class || featureType.getClass() == Cavidade.class)
			{
				EFacemill eFacemill = (EFacemill)model.createEntityInstance(EFacemill.class);
				EMilling_tool_dimension eTool_dimension = createToolDimension(toolDiameter, toolTipAngle, 0, toolEdgeCuttingLength, 0);
				eFacemill.setDimension(null, eTool_dimension);
				eFacemill.setNumber_of_teeth(null, this.toolNumberOfTeeth);
				eFacemill.setHand_of_cut(null, EHand.LEFT);
				eFacemill.setNumber_of_teeth(null, this.toolNumberOfTeeth);
				tool.setIts_tool_body(null, eFacemill);
				ACutting_component aCutting_component = tool.createIts_cutting_edge(null);
				ECutting_component eCutting_component = (ECutting_component)model.createEntityInstance(ECutting_component.class);
				eCutting_component.setTool_offset_length(null, offsetLength);
				aCutting_component.addByIndex(1, eCutting_component);
			}
			else if(featureType.getClass() == FuroBasePlana.class)
			{
				ETwist_drill eTwist_drill = (ETwist_drill)model.createEntityInstance(ETwist_drill.class);
				EMilling_tool_dimension eTool_dimension = createToolDimension(toolDiameter, toolTipAngle, 0, toolEdgeCuttingLength, 0);
				eTwist_drill.setDimension(null, eTool_dimension);
				eTwist_drill.setNumber_of_teeth(null, 2);
				eTwist_drill.setHand_of_cut(null, EHand.LEFT);
				eTwist_drill.setPilot_length(null, offsetLength);
				tool.setIts_tool_body(null, eTwist_drill);
				ACutting_component aCutting_component = tool.createIts_cutting_edge(null);
				ECutting_component eCutting_component = (ECutting_component)model.createEntityInstance(ECutting_component.class);
				eCutting_component.setTool_offset_length(null, offsetLength);
				aCutting_component.addByIndex(1, eCutting_component);
			}
			tool.setIts_id(null, id);
			
		} else if (finishingType == Workingstep.ACABAMENTO)
		{
			if(featureType.getClass() == Ranhura.class ||featureType.getClass() == Degrau.class || featureType.getClass() == Cavidade.class)
			{
				
			}
			else if(featureType.getClass() == FuroBasePlana.class)
			{
				EReamer eReamer = (EReamer)this.model.createEntityInstance(EReamer.class);
				
				
				
				tool.setIts_tool_body(null, eReamer);
			}
			tool.setIts_id(null, id);
		}
		
		return tool;
	}
	private EMilling_tool_dimension createToolDimension(double diameter, double toolTipHalfAngle,  double toolCircunferenceAngle, double cuttingEdgeLength, double edgeRadius) throws SdaiException
	{
		EMilling_tool_dimension eTool_dimension = (EMilling_tool_dimension)model.createEntityInstance(EMilling_tool_dimension.class);
		eTool_dimension.setDiameter(null, diameter);
		eTool_dimension.setTool_tip_half_angle(null, toolTipHalfAngle);
		eTool_dimension.setTool_circumference_angle(null, toolCircunferenceAngle);
		eTool_dimension.setCutting_edge_length(null, cuttingEdgeLength);
		eTool_dimension.setEdge_radius(null, edgeRadius);
		return eTool_dimension;
	}
	private EMilling_tool_dimension createToolDimension(Ferramenta ferramenta) throws SdaiException
	{
		EMilling_tool_dimension eTool_dimension = (EMilling_tool_dimension)this.model.createEntityInstance(EMilling_tool_dimension.class);
		eTool_dimension.setDiameter(null, ferramenta.getDiametroFerramenta());
		eTool_dimension.setTool_tip_half_angle(null, ferramenta.getToolTipHalfAngle() * Math.PI / 180);
		eTool_dimension.setTool_circumference_angle(null, ferramenta.getToolCircunferenceAngle());
		eTool_dimension.setCutting_edge_length(null, ferramenta.getCuttingEdgeLength());
		eTool_dimension.setEdge_radius(null, ferramenta.getEdgeRadius());
		eTool_dimension.setEdge_center_vertical(null, ferramenta.getEdgeCenterVertical());
		eTool_dimension.setEdge_center_horizontal(null, ferramenta.getEdgeCenterHorizontal());
		return eTool_dimension;
	}
	private ETechnology createTechnology(double feedrate, double spindleRot, double vc) throws SdaiException
	{
		EMilling_technology eMilling_technology = (EMilling_technology)model.createEntityInstance(EMilling_technology.class);
		eMilling_technology.setFeedrate(null, feedrate);
		eMilling_technology.setFeedrate_reference(null, ETool_reference_point.TCP);
		eMilling_technology.setSpindle(null, spindleRot);
		eMilling_technology.setSynchronize_spindle_with_feed(null, Boolean.FALSE);
		eMilling_technology.setInhibit_feedrate_override(null, Boolean.FALSE);
		eMilling_technology.setInhibit_spindle_override(null, Boolean.FALSE);
		eMilling_technology.setCutspeed(null, vc);
		
		return eMilling_technology;
	}
	private EMachine_functions createMachineFunctions(boolean coolant) throws SdaiException
	{
		EMilling_machine_functions functions = (EMilling_machine_functions)model.createEntityInstance(EMilling_machine_functions.class);
		functions.setCoolant(null, coolant);
		functions.setChip_removal(null, Boolean.TRUE);
		functions.setThrough_spindle_coolant(null, coolant);
		functions.createAxis_clamping(null);
		functions.createOther_functions(null);
		return functions;
	}
	/**
	 *  Cria estrategia de usinagem
	 * @param strategy
	 * @return
	 * @throws SdaiException
	 */
	private ETwo5d_milling_strategy createStrategy(Two5DMillingStrategy strategy) throws SdaiException
	{
		if(strategy.getClass() == ContourParallel.class)
		{
			ContourParallel contour = (ContourParallel)strategy;
			EContour_parallel eStrategy = (EContour_parallel)model.createEntityInstance(EContour_parallel.class);
			eStrategy.setAllow_multiple_passes(null, contour.isAllowMultiplePasses());
			eStrategy.setOverlap(null, strategy.getOverLap());
			if(contour.getRotationDirection() == RotationDirection.CCW)
				eStrategy.setRotation_direction(null, ERot_direction.CCW);
			else
				eStrategy.setRotation_direction(null, ERot_direction.CW);
			if(contour.getCutmodeType() == ContourParallel.conventional)
				eStrategy.setCutmode(null, ECutmode_type.CONVENTIONAL);
			else
				eStrategy.setCutmode(null, ECutmode_type.CLIMB);
			return eStrategy;
		}else if(strategy.getClass() == TrochoidalAndContourParallelStrategy.class)
		{
			TrochoidalAndContourParallelStrategy trochoidal = (TrochoidalAndContourParallelStrategy)strategy;
			ETrochoidal_and_contourn_parallel etrochoidal = (ETrochoidal_and_contourn_parallel)model.createEntityInstance(ETrochoidal_and_contourn_parallel.class);
			etrochoidal.setAllow_multiple_passes(null, trochoidal.isAllowMultiplePasses());
			etrochoidal.setOverlap(null, strategy.getOverLap());
			if(trochoidal.isRotationDirectionCCW())
				etrochoidal.setRotation_direction(null, ERot_direction.CCW);
			else
				etrochoidal.setRotation_direction(null, ERot_direction.CW);
			if(trochoidal.getCutmodeType() == TrochoidalAndContourParallelStrategy.conventional)
				etrochoidal.setCut_mode(null, ECutmode_type.CONVENTIONAL);
			else
				etrochoidal.setCut_mode(null, ECutmode_type.CLIMB);
			etrochoidal.setTrochoidal_radius(null, trochoidal.getTrochoidalRadius());
			if(trochoidal.getTrochoidalSense() == TrochoidalAndContourParallelStrategy.CCW)
				etrochoidal.setTrochoidal_rot_direction(null, ERot_direction.CCW);
			else
				etrochoidal.setTrochoidal_rot_direction(null, ERot_direction.CW);
			
			return etrochoidal;
		} else if(strategy.getClass() == Bidirectional.class)
		{
			EBidirectional eBidirectional = (EBidirectional)model.createEntityInstance(EBidirectional.class);
			eBidirectional.setAllow_multiple_passes(null, true);
			eBidirectional.setOverlap(null, strategy.getOverLap());
			return eBidirectional;
		}
		return null;
	}
	/**
	 *  Cria estrategia de mergulho
	 * @param operation
	 * @return
	 * @throws SdaiException
	 */
	private EPlunge_strategy createPlungeStrategy(Two5DMillingOperation operation) throws SdaiException
	{
		ApproachRetractStrategy plungeStrategy = operation.getApproachStrategy();
		if(plungeStrategy.getClass().equals(PlungeToolAxis.class))
		{
			EPlunge_toolaxis ePlungeStrategy = (EPlunge_toolaxis)model.createEntityInstance(EPlunge_toolaxis.class);
			ePlungeStrategy.setTool_orientation(null, createDirection("tool orientation", plungeStrategy.getToolDirection()));
			return ePlungeStrategy; 
		} else if(plungeStrategy.getClass().equals(PlungeRamp.class))
		{
			PlungeRamp ramp = (PlungeRamp)plungeStrategy;
			EPlunge_ramp ePlungeStrategy = (EPlunge_ramp)model.createEntityInstance(EPlunge_ramp.class);
			ePlungeStrategy.setTool_orientation(null, createDirection("tool orientation", ramp.getToolDirection()));
			ePlungeStrategy.setAngle(null, ramp.getAngle());
			return ePlungeStrategy;
		} else if(plungeStrategy.getClass().equals(PlungeZigzag.class))
		{
			PlungeZigzag zigZag = (PlungeZigzag)plungeStrategy;
			EPlunge_zigzag ePlungeStrategy = (EPlunge_zigzag)model.createEntityInstance(EPlunge_zigzag.class);
			ePlungeStrategy.setTool_orientation(null, createDirection("tool orientation", zigZag.getToolDirection()));
			ePlungeStrategy.setAngle(null, zigZag.getAngle());
			ePlungeStrategy.setWidth(null, zigZag.getWidth());
			return ePlungeStrategy;
		} else if(plungeStrategy.getClass().equals(PlungeHelix.class))
		{
			PlungeHelix helix = (PlungeHelix)plungeStrategy;
			EPlunge_helix ePlungeStrategy = (EPlunge_helix)model.createEntityInstance(EPlunge_helix.class);
			ePlungeStrategy.setTool_orientation(null, createDirection("tool orientation", helix.getToolDirection()));
			ePlungeStrategy.setAngle(null, helix.getAngle());
			ePlungeStrategy.setRadius(null, helix.getRadius());
			return ePlungeStrategy;
		}
		else 
		{
			return null;
		}
	}
//	public void closeProject() throws SdaiException {
//		session.getActiveTransaction().endTransactionAccessCommit();
//		repository.closeRepository();
//		repository.deleteRepository();
//		session.closeSession();
//	}
	
//	public void closeSession() throws SdaiException, InterruptedException {
//		SdaiTransaction sdaiTransaction = session.getActiveTransaction();
//		sdaiTransaction.commit();
//		sdaiTransaction.abort();
//		
//		session.getActiveTransaction().endTransactionAccessCommit();
//		repository.closeRepository();
//		repository.deleteRepository();
////		session.getSystemRepository().closeRepository();
////		session.getSystemRepository().deleteRepository();
//		session.closeSession();
//	}
	
	private void createHeaderData() throws SdaiException
	{
		String author = this.projeto.getDadosDeProjeto().getUserName();
		String description = this.projeto.getDadosDeProjeto().getDescription();
		String organization = this.projeto.getDadosDeProjeto().getOrganization();
		String preprocess = "ST-GENERATOR 5.3";
		A_string descriptions = this.repository.getDescription();
		descriptions.addByIndex(1, description);
		A_string authors = this.repository.getAuthor();
		authors.addByIndex(1, author);
		A_string organizations = this.repository.getOrganization();
		organizations.addByIndex(1, organization);
		this.repository.setPreprocessorVersion(preprocess);
	}
	
	private void generateFile21() throws SdaiException 
	{
		repository.exportClearTextEncoding(this.projeto.getDadosDeProjeto().getProjectName() + ".p21");
		System.out.println("======= DONE=======");
	}
	public Projeto getProjeto() {
		return projeto;
	}
}
