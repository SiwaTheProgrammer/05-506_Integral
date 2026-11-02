import ru.gr05507.application.CalculationService;
import ru.gr05507.infrastructure.Exp4jFormulaParser;
import ru.gr05507.infrastructure.InMemoryCalculationRepository;
import ru.gr05507.ui.CalculatorWindow;

void main() {
    CalculationService service = new CalculationService(
            new InMemoryCalculationRepository(),
            new Exp4jFormulaParser()
    );
    new CalculatorWindow(service).setVisible(true);
}