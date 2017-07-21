package com.msrm.jdk8.lambdaexpression.helper;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.util.CsvContext;

public class DataProvider {

	public static List<City> cities() {

		List<City> cities = new ArrayList<City>();

		try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader("data.csv"),
				CsvPreference.STANDARD_PREFERENCE)) {

			// the name mapping provide the basis for bean setters
			final String[] nameMapping = new String[] { "id", "name", "state", "cityType", "population",
					"populationClass" };
			// just read the header, so that it don't get mapped to Employee object
			beanReader.getHeader(true);

			final CellProcessor[] processors = new CellProcessor[] { new ParseInt(), // ID (must be unique)
					new NotNull(), // Name
					new NotNull(), // State
					new Optional(), // cityType
					new StringCellProcessor() {
						@SuppressWarnings("unchecked")
						@Override
						public Long execute(Object value, CsvContext context) {
							return Long.parseLong(new String(value.toString()).replaceAll(",", ""));
						}
					}, // Population
					new Optional() // populationClass
			};

			City city;

			while ((city = beanReader.read(City.class, nameMapping, processors)) != null) {
				cities.add(city);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return cities;

	}

}
