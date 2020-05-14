package Controller.AI.ML;

import org.deeplearning4j.nn.graph.ComputationGraph;

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
            return null;
        }
    }

    public static final ModelLoader getInstance()
    {
        return instance;
    }
}