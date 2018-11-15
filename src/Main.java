import com.google.gson.Gson;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static class Map extends Mapper<Object, Text, Text, Text>{
        private Text clau = new Text();
        private Text valor = new Text();
        private static final Gson GSON = new Gson();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            System.out.println("Clau "+key);
            Registre registre = null;
            String camps[] = value.toString().split("#");
            switch(camps[0]){
                case "E":
                    if(!Estudiant.filtrar(camps[1])){
                        registre = new Estudiant(camps[1]);
                    }
                    break;
                case "M":
                    if(!Matricula.filtrar(camps[1])){
                        registre = new Matricula(camps[1]);
                    }
                    break;
            }
            if(registre != null){
                clau.set(camps[1].split(";")[0]);
                valor.set(GSON.toJson(registre));
                context.write(clau, valor);
            }
        }
    }

    public static class Reduce extends Reducer<Text, Text, Text, Text> {
        private static final Gson GSON = new Gson();
        private Text valor = new Text();
        private Text clau = new Text("");
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            Estudiant e = null;
            List<Matricula> matricules = new LinkedList<>();
            Iterator<Text> iterador = values.iterator();
            while(iterador.hasNext()){
                String text = iterador.next().toString();
                Registre registre = GSON.fromJson(text, Registre.class);
                switch(registre.getTipus()){
                    case ESTUDIANT:
                        e = GSON.fromJson(text, Estudiant.class);
                        break;
                    case MATRICULA:
                        matricules.add(GSON.fromJson(text, Matricula.class));
                        break;
                }
            }
            if(e != null && !matricules.isEmpty()){
                for(Matricula matricula: matricules){
                    valor.set(e+", "+matricula.toString());
                    context.write(clau, valor);
                }
            }
        }
    }

    private static void help(){
        System.out.println("Instruccions d'ús");
        System.out.println("hadoop jar arxiu.jar Main estudiants.txt matricules.txt desti");
    }

    public static void main(String[] args) throws Exception {
        if(args.length != 3){
            help();
            throw new Exception();
        }
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Exercici 4");
        job.setJarByClass(Main.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileInputFormat.addInputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
