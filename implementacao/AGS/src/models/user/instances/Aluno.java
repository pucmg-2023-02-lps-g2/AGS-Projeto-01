package models.user.instances;

import exceptions.ObjectNotFoundException;
import managers.DisciplinaManager;
import models.Disciplina;
import models.user.IPessoa;
import system.AGS;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Aluno implements IPessoa {

    // Atributos
    private List<Disciplina> gradeCurricular;

    // Construtores
    public Aluno() {
        this.gradeCurricular = new ArrayList<Disciplina>();
    }

    // Getters e Setters
    public List<Disciplina> getGradeCurricular() {
        return gradeCurricular;
    }

    // Métodos
    public void realizarMatriculaNaMemoria(Disciplina disciplina) {
    	this.gradeCurricular.add(disciplina);
    }

    public void matricularEmDisciplina() throws ObjectNotFoundException {

        if (!AGS.isPeriodoMatricula()) {
            System.out.println("Erro: Não é possível realizar matrículas no momento!");
            return;
        }

        if (gradeCurricular.size() >= 6) {
            System.out.println("Erro: Você já se matriculou no máximo permitido de disciplinas!");
            return;
        }

        DisciplinaManager.getInstance().exibirDisciplinas();

        System.out.println("Digite o id da disciplina que gostaria de se matricular");

        String id = new Scanner(System.in).nextLine();

        Disciplina disciplina = DisciplinaManager.getInstance().findDisciplina(id);

        boolean matriculado = this.gradeCurricular.add(disciplina);

        // Talvez dê erro, pq collection so retorna "true", não false
        if (matriculado) {
            System.out.println("Matriculado na disciplina: " + disciplina.getNome());
        } else {
            System.out.println("Você já está matriculado na disciplina " + disciplina.getNome());
        }
    }
    
    public void cancelarMatriculaDisciplina(Disciplina disciplina) {
    	this.gradeCurricular.remove(disciplina);
    }

    public void cancelarMatriculaDisciplina() throws ObjectNotFoundException {
        DisciplinaManager.getInstance().exibirDisciplinas();

        System.out.println("Digite o id da disciplina da qual você gostaria de se desmatricular");

        String id = new Scanner(System.in).nextLine();

        Disciplina disciplina = DisciplinaManager.getInstance().findDisciplina(id);

        boolean desmatriculado = this.gradeCurricular.remove(disciplina);

        if (desmatriculado) {
            System.out.println("Você foi desmatriculado da disciplina " + disciplina.getNome());
        } else {
            System.out.println("Você não está matriculado na disciplina " + disciplina.getNome());
        }
    }

    public void verGradeCurricular() {
        System.out.println("Matriculado nas seguintes disciplinas: ");
        this.gradeCurricular.forEach(disciplina -> System.out.println(disciplina.getNome()));
    }

    @Override
    public void exibirMenu() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("1 - Matricular");
            System.out.println("2 - Cancelar Matricula");
            System.out.println("3 - Ver grade");

            System.out.print("OP: ");
            String option = scanner.nextLine();

            switch (Integer.parseInt(option)) {
                case 1:
                    matricularEmDisciplina();
                    break;
                case 2:
                    cancelarMatriculaDisciplina();
                    break;
                case 3:
                    verGradeCurricular();
                    break;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
