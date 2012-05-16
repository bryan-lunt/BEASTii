/*
 * Copyright (C) 2012 Tim Vaughan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package test.beast.evolution.operators;

import beast.core.MCMC;
import beast.core.State;
import beast.evolution.tree.TreeReport;
import beast.core.parameter.RealParameter;
import beast.evolution.alignment.Alignment;
import beast.evolution.alignment.Sequence;
import beast.evolution.alignment.Taxon;
import beast.evolution.operators.WilsonBalding;
import beast.evolution.tree.RandomTree;
import beast.evolution.tree.Tree;
import beast.evolution.tree.coalescent.Coalescent;
import beast.evolution.tree.coalescent.ConstantPopulation;
import beast.evolution.tree.coalescent.TreeIntervals;
import beast.evolution.tree.TreeTraceAnalysis;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.Assert;

/**
 * Tests whether the distribution of tree topologies generated by the
 * WilsonBalding operator under a coalescent model is sensible or not.
 *
 * @author Tim Vaughan
 */
public class WilsonBaldingTest {

		// Array of distinct topologies for trees with 4 taxa.
		String [] topologies = {
			"((0,2),(1,3))",
			"((0,1),(2,3))",
			"((0,3),(1,2))",
			"(((1,2),3),0)",
			"(((1,3),0),2)",
			"(((0,1),2),3)",
			"(((0,2),3),1)",
			"(((0,3),2),1)",
			"(((2,3),1),0)",
			"(((0,2),1),3)",
			"(((0,1),3),2)",
			"(((2,3),0),1)",
			"(((1,2),0),3)",
			"(((1,3),2),0)",
			"(((0,3),1),2)"
		};

		// Relative frequencies with which each of the topologies should
		// occur in trace.
		double [] probs = {
			0.133,
			0.133,
			0.133,
			0.067,
			0.067,
			0.067,
			0.067,
			0.067,
			0.067,
			0.067,
			0.067,
			0.067,
			0.067,
			0.067,
			0.067
		};

	/**
	 * Interface with JUnit 3.* test runner:
	 * @return 
	 */
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(WilsonBaldingTest.class);
	}

	/**
	 * Test topology distribution.
	 * @throws Exception 
	 */
	@Test
	public void topologyDistribution() throws Exception {

		// Assemble model:
		
		ConstantPopulation constantPop = new ConstantPopulation();
		constantPop.initByName("popSize", new RealParameter("10000.0"));

		List<Object> alignmentInitArgs = new ArrayList<Object>();
		for (int i=0; i<4; i++) {
			Sequence thisSeq = new Sequence();
			thisSeq.initByName("taxon", String.valueOf(i), "value", "?");
			alignmentInitArgs.add("sequence");
			alignmentInitArgs.add(thisSeq);
		}
		Alignment alignment = new Alignment();
		alignment.initByName(alignmentInitArgs.toArray());

		Tree tree = new RandomTree();
		tree.initByName("taxa", alignment, "populationModel", constantPop);

		TreeIntervals treeIntervals = new TreeIntervals();
		treeIntervals.initByName("tree", tree);
		Coalescent coalescentDistrib = new Coalescent();
		coalescentDistrib.initByName(
				"treeIntervals", treeIntervals,
				"populationModel", constantPop
				);

		// Set up state:
		State state = new State();
		state.initByName("stateNode", tree);

		// Set up operator:
		WilsonBalding wilsonBalding = new WilsonBalding();
		wilsonBalding.initByName("weight", "1", "tree", tree);

		// Set up logger:
		TreeReport treeReport = new TreeReport();
		treeReport.initByName(
				"logEvery", "100",
				"burnin", "1000",
				"credibleSetProb", "0.95",
				"log", tree,
				"silent", true
				);

		// Set up MCMC:
		MCMC mcmc = new MCMC();
		mcmc.initByName(
				"chainLength", "2000000",
				"state", state,
				"distribution", coalescentDistrib,
				"operator", wilsonBalding,
				"logger", treeReport
				);

		// Run MCMC:
		mcmc.run();

		// Obtain analysis results:
		TreeTraceAnalysis analysis = treeReport.getAnalysis();
		Map<String,Integer> topologyCounts = analysis.getTopologyCounts();
		int totalTreesUsed = analysis.getTotalTreesUsed();

		// Test topology distribution against ideal:
		for (int i=0; i<topologies.length; i++) {
			double thisProb = topologyCounts.get(topologies[i])
					/(double)totalTreesUsed;
			boolean withinTol = (thisProb > probs[i]-0.03
					&& thisProb < probs[i]+0.03);

			Assert.assertTrue(withinTol);

			System.err.format("Topology %s rel. freq. %.3f",
					topologies[i], thisProb);
			if (withinTol)
				System.err.println(" (Within tolerance 0.03 of "
						+ String.valueOf(probs[i]) + ")");
			else
				System.err.println(" (FAILURE: outside tolerance 0.03 of "
						+ String.valueOf(probs[i]) + ")");
		}

	}

}
