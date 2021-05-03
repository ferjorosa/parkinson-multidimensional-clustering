package scripts;

import eu.amidst.core.datastream.Attribute;
import eu.amidst.core.datastream.DataInstance;
import eu.amidst.core.datastream.DataOnMemory;
import eu.amidst.core.io.DataStreamLoader;
import eu.amidst.extension.data.DataUtils;
import eu.amidst.extension.util.LogUtils;
import methods.GLCM;

import java.util.List;
import java.util.stream.Collectors;

public class Learn_GLCM {

    public static void main(String[] args) throws Exception {
        long seed = 0;
        String settingsLocation = "geast_settings.xml";
        LogUtils.LogLevel logLevel= LogUtils.LogLevel.INFO;

        String fileName = "data/data_numerical_imputed.arff";

        learnWithImputed(seed, fileName, settingsLocation, logLevel);

    }

    private static void learnWithImputed(long seed,
                                         String fileName,
                                         String settingsLocation,
                                         LogUtils.LogLevel logLevel) throws Exception {

        DataOnMemory<DataInstance> data = DataStreamLoader.loadDataOnMemoryFromFile(fileName);

        /* Filter the socio-demographic attributes */
        List<Attribute> symptomAttributes = data.getAttributes().getFullListOfAttributes().stream()
                .filter(x->
                        !x.getName().equals("patnum") &&
                                !x.getName().equals("age") &&
                                !x.getName().equals("sex") &&
                                !x.getName().equals("pdonset") &&
                                !x.getName().equals("durat_pd") &&
                                !x.getName().equals("hy")
                )
                .collect(Collectors.toList());
        data = DataUtils.project(data, symptomAttributes);

        GLCM.learnModel(data, settingsLocation, logLevel, seed);
    }
}