package org.opencompare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencompare.api.java.*;
import org.opencompare.api.java.util.PCMVisitor;
import org.opencompare.api.java.value.*;

/**
 * 
 * @author Nantenin
 *
 */
public class CellTypePCMVisitor implements PCMVisitor {

	private Map<String, List<String>> cellsTypes = new HashMap<>();
	private String currentCellKey;

	private void addType(String key, String type) {
		if (cellsTypes.get(key) == null) {
			cellsTypes.put(key, new ArrayList<>());
		}
		cellsTypes.get(key).add(type);
	}

	public Map<String, List<String>> getResult() {
		return cellsTypes;
	}
	// Methods for the visitor

	@Override
	public void visit(PCM pcm) {
		for (Product product : pcm.getProducts()) {
			product.accept(this);
		}
	}

	@Override
	public void visit(Feature feature) {

	}

	@Override
	public void visit(FeatureGroup featureGroup) {

	}

	@Override
	public void visit(Product product) {
		for (Cell cell : product.getCells()) {
			cell.accept(this);
		}
	}

	@Override
	public void visit(Cell cell) {
		Value interpretation = cell.getInterpretation();
		if (interpretation != null) {
			// TODO - set here current col name.
			currentCellKey = cell.getFeature().getName();

			interpretation.accept(this);
		}
	}

	@Override
	public void visit(BooleanValue booleanValue) {
		addType(currentCellKey, BooleanValue.class.getSimpleName());
	}

	@Override
	public void visit(Conditional conditional) {
		addType(currentCellKey, Conditional.class.getSimpleName());
	}

	@Override
	public void visit(DateValue dateValue) {
		addType(currentCellKey, DateValue.class.getSimpleName());
	}

	@Override
	public void visit(Dimension dimension) {
		addType(currentCellKey, Dimension.class.getSimpleName());
	}

	@Override
	public void visit(IntegerValue integerValue) {
		addType(currentCellKey, IntegerValue.class.getSimpleName());
	}

	@Override
	public void visit(Multiple multiple) {
		for (Value value : multiple.getSubValues()) {
			value.accept(this);
		}
	}

	@Override
	public void visit(NotApplicable notApplicable) {
		// TODO - process if necessary
	}

	@Override
	public void visit(NotAvailable notAvailable) {
		// TODO - process if necessary
	}

	@Override
	public void visit(Partial partial) {
		// TODO - process if necessary
		// addType(currentCellKey, Partial.class.getSimpleName());
	}

	@Override
	public void visit(RealValue realValue) {
		addType(currentCellKey, RealValue.class.getSimpleName());
	}

	@Override
	public void visit(StringValue stringValue) {
		addType(currentCellKey, StringValue.class.getSimpleName());
	}

	@Override
	public void visit(Unit unit) {
		addType(currentCellKey, Unit.class.getSimpleName());
	}

	@Override
	public void visit(Version version) {
		addType(currentCellKey, Version.class.getSimpleName());
	}
}
