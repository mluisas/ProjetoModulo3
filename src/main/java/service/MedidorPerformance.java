package service;

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



}
