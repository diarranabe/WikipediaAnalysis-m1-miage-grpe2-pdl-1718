package org.opencompare;


import org.knowm.xchart.internal.chartpart.Chart;

public interface PCMChart<C extends Chart<?, ?>> {

	C getChart();
}