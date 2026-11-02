import ru.gr05506.application.CalculationService;
import ru.gr05506.infrastructure.Exp4jFormulaParser;
import ru.gr05506.infrastructure.InMemoryCalculationRepository;
import ru.gr05506.ui.CalculatorWindow;

void main() {
    CalculationService service = new CalculationService(
            new InMemoryCalculationRepository(),
            new Exp4jFormulaParser()
    );
    new CalculatorWindow(service).setVisible(true);
}