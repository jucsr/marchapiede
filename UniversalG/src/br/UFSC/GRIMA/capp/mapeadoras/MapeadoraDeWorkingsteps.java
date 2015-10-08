package br.UFSC.GRIMA.capp.mapeadoras;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.InfoMovimentacao;
import br.UFSC.GRIMA.capp.OrdenadoraDeWorkingsteps;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CavidadeFundoArredondado;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;
import br.UFSC.GRIMA.entidades.features.FuroBaseConica;
import br.UFSC.GRIMA.entidades.features.FuroBaseEsferica;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.FuroConico;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilBezier;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilCircularParcial;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilRoundedU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilVee;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.Reamer;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.util.Ponto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraDeWorkingsteps {
	
	private Vector<Face> faces;// recebe
	private CondicoesDeUsinagem condicoesDeUsinagem;
	private Vector<Vector<Workingstep>> workingsteps;// saida - vetor de faces
														// com as workingsteps
	private Projeto projeto;
	
	public MapeadoraDeWorkingsteps() {

	}

	public MapeadoraDeWorkingsteps(Projeto projeto) {
		this.projeto = projeto;
		

	}

	public MapeadoraDeWorkingsteps(Vector faces,
			CondicoesDeUsinagem condicoesDeUsinagem) {
		this.faces = faces;
		this.condicoesDeUsinagem = condicoesDeUsinagem;
		this.imprimirDados();
	}

	
	public static Ponto[] determinadorDePontos(Workingstep workingstep) {
		Feature feature = workingstep.getFeature();
		System.out.println("Dados br.UFSC.GRIMA.feature: "
				+ feature.getTipoString() + " posicao: "
				+ feature.getPosicaoX() + " " + feature.getPosicaoY() + " "
				+ feature.getPosicaoZ() + " ");
		// workingstep.setFerramenta(new Ferramenta());
		Ponto[] ponto = null;
		boolean acabamento = false;
		double dZ = 0, x, y, z;
		switch (feature.getTipo()) {
		case Feature.FURO:
			Furo furo = (Furo) feature;
			ponto = new Ponto[1];
			Ponto p = new Ponto(furo.getPosicaoX(), furo.getPosicaoY(), furo
					.getPosicaoZ()
					- dZ);
			ponto[0] = p;
			break;
		case Feature.DEGRAU:
			Degrau degrau = (Degrau) feature;
			ponto = new Ponto[4];
			if (acabamento) {
				// faz um negocio
			} else {
				if (degrau.getEixo() == Degrau.HORIZONTAL) {
					if (degrau.getPosicaoY() == 0) {
						x = degrau.getPosicaoX();
						y = degrau.getPosicaoY();
						z = degrau.getPosicaoZ() - dZ;
						ponto[0] = new Ponto(x, y, z);
						y = degrau.getLargura()
								- workingstep.getFerramenta()
										.getDiametroFerramenta() / 2;
						ponto[1] = new Ponto(x, y, z);
						x = workingstep.getFace().getComprimento();
						ponto[2] = new Ponto(x, y, z);
						y = degrau.getPosicaoY();
						ponto[3] = new Ponto(x, y, z);
					} else {
						x = degrau.getPosicaoX();
						y = workingstep.getFace().getLargura();
						z = degrau.getPosicaoZ() - dZ;
						ponto[0] = new Ponto(x, y, z);
						y = degrau.getPosicaoY()
								+ workingstep.getFerramenta()
										.getDiametroFerramenta() / 2;
						ponto[1] = new Ponto(x, y, z);
						x = workingstep.getFace().getComprimento();
						ponto[2] = new Ponto(x, y, z);
						y = workingstep.getFace().getLargura();
						ponto[3] = new Ponto(x, y, z);
					}
				} else {
					if (degrau.getPosicaoX() == 0) {
						x = degrau.getPosicaoX();
						y = degrau.getPosicaoY();
						z = degrau.getPosicaoZ() - dZ;
						ponto[0] = new Ponto(x, y, z);
						y = workingstep.getFace().getLargura();
						ponto[1] = new Ponto(x, y, z);
						x = degrau.getLargura()
								- workingstep.getFerramenta()
										.getDiametroFerramenta() / 2;
						ponto[2] = new Ponto(x, y, z);
						y = degrau.getPosicaoY();
						ponto[3] = new Ponto(x, y, z);
					} else {
						x = workingstep.getFace().getComprimento();
						y = degrau.getPosicaoY();
						z = degrau.getPosicaoZ() - dZ;
						ponto[0] = new Ponto(x, y, z);
						y = workingstep.getFace().getLargura();
						ponto[1] = new Ponto(x, y, z);
						x = degrau.getPosicaoX()
								+ workingstep.getFerramenta()
										.getDiametroFerramenta() / 2;
						ponto[2] = new Ponto(x, y, z);
						y = degrau.getPosicaoY();
						ponto[3] = new Ponto(x, y, z);
					}
				}
			}
			break;
		case Feature.RANHURA:
			Ranhura ranhura = (Ranhura) feature;
			ponto = new Ponto[4];
			if (ranhura.getEixo() == Ranhura.HORIZONTAL) {
				if (acabamento) {

				} else {
					x = ranhura.getPosicaoX();
					y = ranhura.getPosicaoY()
							+ workingstep.getFerramenta()
									.getDiametroFerramenta() / 2;
					z = ranhura.getPosicaoZ() - dZ;
					Ponto rP0 = new Ponto(x, y, z);
					ponto[0] = rP0;
					y = ranhura.getPosicaoY()
							+ ranhura.getLargura()
							- workingstep.getFerramenta()
									.getDiametroFerramenta() / 2;
					Ponto rP1 = new Ponto(x, y, z);
					ponto[1] = rP1;
					x += workingstep.getFace().getComprimento();
					Ponto rP2 = new Ponto(x, y, z);
					ponto[2] = rP2;
					y = ranhura.getPosicaoY()
							+ workingstep.getFerramenta()
									.getDiametroFerramenta() / 2;
					Ponto rP3 = new Ponto(x, y, z);
					ponto[3] = rP3;
				}
			} else {
				if (acabamento) {

				} else {
					x = ranhura.getPosicaoX()
							+ workingstep.getFerramenta()
									.getDiametroFerramenta() / 2;
					y = ranhura.getPosicaoY();
					z = ranhura.getPosicaoZ() - dZ;
					Ponto rP0 = new Ponto(x, y, z);
					ponto[0] = rP0;
					y += workingstep.getFace().getLargura();
					Ponto rP1 = new Ponto(x, y, z);
					ponto[1] = rP1;
					x = ranhura.getPosicaoX()
							+ ranhura.getLargura()
							- workingstep.getFerramenta()
									.getDiametroFerramenta() / 2;
					Ponto rP2 = new Ponto(x, y, z);
					ponto[2] = rP2;
					y -= workingstep.getFace().getLargura();
					Ponto rP3 = new Ponto(x, y, z);
					ponto[3] = rP3;
				}
			}
			break;
		case Feature.CAVIDADE:
			ponto = new Ponto[2];
			Cavidade cavidade = (Cavidade) feature;
			z = cavidade.getPosicaoZ() - dZ;
			if (cavidade.getLargura() < cavidade.getComprimento()) {
				x = cavidade.getPosicaoX() + cavidade.getLargura() / 2;
				y = cavidade.getPosicaoY() + cavidade.getLargura() / 2;
				ponto[0] = new Ponto(x, y, z);
				x = 0;
				x = cavidade.getPosicaoX() + cavidade.getComprimento()
						- cavidade.getLargura() / 2;
				ponto[1] = new Ponto(x, y, z);
			} else {
				x = cavidade.getPosicaoX() + cavidade.getComprimento() / 2;
				y = cavidade.getPosicaoY() + cavidade.getComprimento() / 2;
				ponto[0] = new Ponto(x, y, z);
				y = 0;
				y = cavidade.getPosicaoY() + cavidade.getLargura()
						- cavidade.getComprimento() / 2;
				ponto[1] = new Ponto(x, y, z);
			}

			break;
		default:
			break;
		}
		/*
		 * for(int pointIndex = 0; pointIndex < ponto.length; pointIndex++){
		 * System.out.println("ponto[" + pointIndex + "] = " +
		 * ponto[pointIndex].getX() + " " + ponto[pointIndex].getY() + " " +
		 * ponto[pointIndex].getZ()); }
		 */

		return ponto;
	}

	public InfoMovimentacao determinarInfoMovimentacao(Workingstep ws) {
		InfoMovimentacao infoMovimentacao = new InfoMovimentacao();
		Feature feature = ws.getFeature();
		switch (feature.getTipo()) {
		case Feature.FURO:
			infoMovimentacao.setTipo(InfoMovimentacao.RADIAL_FURO);
			break;
		case Feature.DEGRAU:
			infoMovimentacao.setTipo(InfoMovimentacao.ZIG_ZAG);
			break;
		case Feature.RANHURA:
			infoMovimentacao.setTipo(InfoMovimentacao.ZIG_ZAG);
			break;
		case Feature.CAVIDADE:
			infoMovimentacao.setTipo(InfoMovimentacao.RADIAL_CAVIDADE);
			break;
		default:
			break;
		}
		return infoMovimentacao;
	}

	public void imprimirDados() {
		System.out.println("\n");
		System.out
				.println("=======================================================");
		System.out
				.println("==        Dados da Mapeadora de Workingsteps         ==");
		System.out
				.println("=======================================================");

		for (int i = 0; i < this.workingsteps.size(); i++) {
			Vector workingstepsFaceI = (Vector) this.workingsteps.elementAt(i);

			System.out.printf("..:: Face #%d (possui %d workingsteps) ::..", i,
					workingstepsFaceI.size());

			for (int j = 0; j < workingstepsFaceI.size(); j++) {
				Workingstep wsJ = (Workingstep) workingstepsFaceI.elementAt(j);

				System.out.printf("\tWorkingstep #%d\n", j);
				System.out.println(wsJ.getDados("\t"));

			}
			System.out.println("\n");
		}

		System.out
				.println("=======================================================");
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public void setWorkingsteps(Vector<Vector<Workingstep>> workingsteps) {
		this.workingsteps = workingsteps;
	}
}
