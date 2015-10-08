package br.UFSC.GRIMA.capp.movimentacoes;

import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.util.Path;

public class GenerateContourParallelMovement 
{
	public ArrayList<Path> movimentacao = new ArrayList<Path>();
	/**
	 * 
	 * @param ws -- workingstep which should generate the contour parallel movement, Notice that the workingstep have to belong to a GeneralClosedPocket feature.
	 */
	public GenerateContourParallelMovement(Workingstep ws)
	{
		MovimentacaoGeneralClosedPocket detMov = new MovimentacaoGeneralClosedPocket(ws);
		this.movimentacao = detMov.getDesbasteContourParallel();
	}
	/**
	 * explicit in the name
	 * @return
	 */
	public ArrayList<Path> getPaths()
	{
		return this.movimentacao;
	}
}