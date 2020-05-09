package Controller.AI;

import org.deeplearning4j.nn.conf.graph.MergeVertex
import org.deeplearning4j.nn.conf.layers.{DenseLayer, LSTM, OutputLayer, RnnOutputLayer}
import org.deeplearning4j.nn.conf.{ComputationGraphConfiguration, MultiLayerConfiguration, NeuralNetConfiguration}
import org.deeplearning4j.nn.graph.ComputationGraph
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.learning.config.Nesterovs
import org.nd4j.linalg.lossfunctions.LossFunctions

public class ModelLoader
{
    private static final ModelLoader instance = new ModelLoader();
    private int modelId = 0;
    private ComputationGraph model = null;

    private ModelLoader()
    {
        System.out.println("Model loader initialized");
    }

    public static ComputationGraph loadModel(int id){
        if(id == getInstance().modelId){
            return getInstance().model;
        }
        else{
            getInstance().modelId = id;
        }
    }

    public static final ModelLoader getInstance()
    {
        return instance;
    }
}