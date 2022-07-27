package service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MedidorPerformance {

    public String nomeMedicao;
    public Instant momentoInicio;
    public Instant momentoFim;
    public List<MedidorPerformance> subMedicoes;

    public MedidorPerformance() {
        momentoInicio = Instant.MIN;
        momentoFim = Instant.MIN;
        subMedicoes = new ArrayList<>();
    }

    public void iniciar(String nomeMedicao){
        MedidorPerformance medidorPerformance = this;
        if(medidorPerformance.momentoInicio.isAfter(Instant.MIN)){
            medidorPerformance = achaMedicaoAtual(medidorPerformance);
            MedidorPerformance novoMedidor = new MedidorPerformance();
            medidorPerformance.subMedicoes.add(novoMedidor);
            medidorPerformance = novoMedidor;
        }

        medidorPerformance.nomeMedicao = nomeMedicao;
        medidorPerformance.momentoInicio = Instant.now();
    }

    private MedidorPerformance achaMedicaoAtual(MedidorPerformance medidorAtual){
        if(medidorAtual.subMedicoes.isEmpty()){
            return medidorAtual;
        } else{
            MedidorPerformance ultimaMedicaoNaLista = medidorAtual.subMedicoes.get(medidorAtual.subMedicoes.size() - 1);
            if(ultimaMedicaoNaLista.momentoFim.isAfter(Instant.MIN)){
                return medidorAtual;
            }
            return achaMedicaoAtual(ultimaMedicaoNaLista);
        }
    }

    public void terminar(){
        MedidorPerformance medidorPerformance = this;
        if(medidorPerformance.momentoInicio.isAfter(Instant.MIN)){
            medidorPerformance = achaMedicaoAtual(medidorPerformance);
        }

        medidorPerformance.momentoFim = Instant.now();
    }

    public void mostraResultado(){
        StringBuilder res = new StringBuilder();

        this.mostraResultadoRecursivo(res, 0);

        System.out.println(res);
    }

    private void mostraResultadoRecursivo(StringBuilder stringAtual, int camada){
        String prefixo = "\t".repeat(camada);
        stringAtual.append(prefixo).append(this.nomeMedicao).append(": {\n");
        for(MedidorPerformance submedicao : this.subMedicoes){
            submedicao.mostraResultadoRecursivo(stringAtual, camada+1);
            stringAtual.append('\n');
        }
        stringAtual.append(prefixo).append("}\n");
        stringAtual.append(prefixo).append("Início: ").append(this.momentoInicio).append('\n');
        stringAtual.append(prefixo).append("Fim: ").append(this.momentoFim).append('\n');
        double ms = Duration.between(this.momentoInicio, this.momentoFim).toNanos() / 1e6;
        stringAtual.append(prefixo).append("Tempo: ").append(ms).append("ms").append('\n');
    }

}
