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

public class NNModel
{
    private ComputationGraph model = null;
    public NNModel(int nb_players)
    {
        int nb_factories = 2*nb_players + 1;

        int factory_layers_out = 5;
        int center_layer_out = 10;
        int factories_layer_size = nb_factories * 6;
        int factories_layer_out = nb_factories * 2;
        int wall_layer_size = 30;
        int wall_layer_out = 20;
        int pyramid_layer_out = 10;
        int board_layer_out = 30;
        int floor_layer_out = 4;
        int player_layer_size = 20;
        int player_layer_out = 20;
        int L7_size = 25 * nb_players;
        int L8_size = 25 * nb_players;

        List<String> in_strs = new ArrayList<String>();
        List<String> fac_strs = new ArrayList<String>();
        List<String> game_strs = new ArrayList<String>();

        in_strs.add("center");
        in_strs.add("center_malus");

        for(int i = 0; i < nb_factories; i++) {
            in_strs.add("factory_" + i);
            fac_strs.add("L1_fac_" + i);
        }
        fac_strs.add("L1_center");
        fac_strs.add("center_malus");

        for(int i = 0; i < nb_players; i++){
            in_strs.add("wall_color_" + i);
            in_strs.add("wall_shape_" + i);
            in_strs.add("floor_" + i);
            in_strs.add("pyramid_color_" + i);
            in_strs.add("pyramid_shape_" + i);
        }
        ComputationGraphConfiguration.GraphBuilder builder = new NeuralNetConfiguration.Builder()
            .seed(123)
            .weightInit(WeightInit.XAVIER)
            .updater(new AdaGrad(0.5)) //High Level Configuration
            .activation(Activation.RELU)
            .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
            .learningRate(0.05)
            .l2(0.0001)
            .graphBuilder();//For configuring ComputationGraph we call the graphBuilder method

        builder.addInputs(in_strs); //Configuring Layers

        for(int i = 0; i < nb_factories; i++) {
            builder.addLayer("L1_fac_"+i, new DenseLayer.Builder().nIn(20).nOut(factory_layers_out).build(), "factory_" + i);
        }
        builder.addLayer("L1_center", new DenseLayer.Builder().nIn(40).nOut(center_layer_out).build(), "factory_" + i);

        Layer tmp = new DenseLayer.Builder().nIn(nb_factories*factory_layers_out + center_layer_out + 1).nOut(factories_layer_size).build();
        builder.addLayer("L2_factories", tmp, fac_strs.toArray(new String[fac_strs.size()]));
        builder.addLayer("L3_factories", new DenseLayer.Builder().nIn(factories_layer_size).nOut(factories_layer_out).build(), "L2_factories");

        for(int i = 0; i < nb_players; i++){
            builder.addLayer("L1_wall_color_"+i, new DenseLayer.Builder().nIn(25).nOut(wall_layer_size).build(), "wall_color_" + i);
            builder.addLayer("L1_wall_shape_"+i, new DenseLayer.Builder().nIn(25).nOut(wall_layer_size).build(), "wall_shape_" + i);
            builder.addLayer("L2_wall_color_"+i, new DenseLayer.Builder().nIn(wall_layer_size).nOut(wall_layer_out).build(), "L1_wall_color_" + i);
            builder.addLayer("L2_wall_shape_"+i, new DenseLayer.Builder().nIn(wall_layer_size).nOut(wall_layer_out).build(), "L1_wall_shape_" + i);
            builder.addLayer("L3_wall_"+i, new DenseLayer.Builder().nIn(wall_layer_out*2).nOut(wall_layer_out).build(), "L2_wall_color_" + i, "L2_wall_shape_" + i);

            builder.addLayer("L1_pyramid_color_"+i, new DenseLayer.Builder().nIn(25).nOut(25).build(), "pyramid_color_" + i);
            builder.addLayer("L1_pyramid_shape_"+i, new DenseLayer.Builder().nIn(15).nOut(15).build(), "pyramid_shape_" + i);
            builder.addLayer("L2_pyramid_"+i, new DenseLayer.Builder().nIn(35).nOut(pyramid_layer_out).build(), "L1_pyramid_color_" + i, "L1_pyramid_shape_" + i);

            builder.addLayer("L4_board_"+i, new DenseLayer.Builder().nIn(wall_layer_out + pyramid_layer_out).nOut(board_layer_out).build(), "L3_wall_" + i, "L2_pyramid_" + i);

            builder.addLayer("L1_floor_"+i, new DenseLayer.Builder().nIn(7).nOut(floor_layer_out).build(), "floor_" + i);
            builder.addLayer("L5_player_"+i, new DenseLayer.Builder().nIn(board_layer_out + floor_layer_out).nOut(player_layer_size).build(), "L4_board_" + i, "L1_floor_" + i);
            builder.addLayer("L6_player_"+i, new DenseLayer.Builder().nIn(player_layer_size).nOut(player_layer_out).build(), "L5_player_" + i);

            game_strs.add("L6_players_" + i);
        }
        game_strs.add("L3_factories");
        builder.addLayer("L7_game", new DenseLayer.Builder().nIn(player_layer_out*4 + factories_layer_out).nOut(L7_size).build(), game_strs.toArray(new String[game_strs.size()]));
        builder.addLayer("L8_game", new DenseLayer.Builder().nIn(L7_size).nOut(L8_size).build(), "L7_game");
        builder.addLayer("prediction", new OutputLayer.Builder().lossFunction(LossFuctions.LossFunction.MSE).nIn(L8_size).nOut(1).build(), "L8_game");
        builder.setOutputs("prediction");

        ComputationGraphConfiguration config = builder.build(); //Building configuration

        model = new ComputationGraph(config);
        model.init();
    }
}