package org.opencompare;

import java.util.ArrayList;
import java.util.List;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class R {

	public static void main(String[] args) {

		try {
			RConnection c = new RConnection();
			REXP x = c.eval("R.version.string");
			System.out.println(x.asString());
			
			List<Integer> tab = new ArrayList<>();
			tab.add(1);
			tab.add(12);
			tab.add(113);
			tab.add(133);
			graphicMatrixSize(c, tab);

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public static void graphicMatrixSize(RConnection c,List<Integer> tab) throws REngineException, REXPMismatchException {
	
		System.out.println(toStringArray(tab));
		REXP x = c.parseAndEval("plot(data.frame("+toStringArray(tab)+"))");
		
		
	}

	private static String toStringArray(List<Integer> tab) {
		
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<tab.size();i++) {
			sb.append(tab.get(i).toString());
			if(i<=tab.size()-2) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

}
