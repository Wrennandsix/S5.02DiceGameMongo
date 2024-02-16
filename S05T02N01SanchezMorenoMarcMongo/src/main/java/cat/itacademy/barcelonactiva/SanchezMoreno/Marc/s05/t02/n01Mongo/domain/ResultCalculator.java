package cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.domain;

@FunctionalInterface
interface ResultCalculator {
    String calculate(int dice1, int dice2);
}