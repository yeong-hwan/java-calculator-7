package calculator.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CalculatorServiceTest {

    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void 빈_문자열_입력_테스트() {
        String input = "";
        int result = calculatorService.calculate(input);
        assertThat(result).isEqualTo(0);
    }

    @Test
    void 기본_구분자_입력_테스트() {
        String input = "1,2:3";
        int result = calculatorService.calculate(input);
        assertThat(result).isEqualTo(6);
    }

    @Test
    void 커스텀_구분자_입력_테스트() {
        String input = "//;\\n1;2;3";
        int result = calculatorService.calculate(input);
        assertThat(result).isEqualTo(6);
    }

    @Test
    void 커스텀_구분자와_기본_구분자_혼합_테스트() {
        String input = "//;\\n1;2,3:4";
        int result = calculatorService.calculate(input);
        assertThat(result).isEqualTo(10);
    }

    @Test
    void 음수_입력_예외_테스트() {
        String input = "-1,2,3";
        assertThatThrownBy(() -> calculatorService.calculate(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 음수는 허용되지 않습니다");
    }

    @Test
    void 잘못된_숫자_입력_예외_테스트() {
        String input = "1,a,3";
        assertThatThrownBy(() -> calculatorService.calculate(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 잘못된 입력 값: a");
    }

    @Test
    void 단일_숫자_입력_테스트() {
        String input = "5";
        int result = calculatorService.calculate(input);
        assertThat(result).isEqualTo(5);
    }

    @Test
    void 공백_포함_숫자_입력_테스트() {
        String input = "  1,  2  ,3 ";
        int result = calculatorService.calculate(input);
        assertThat(result).isEqualTo(6);
    }

    @Test
    void 연속된_커스텀_구분자_입력_테스트() {
        String input = "//***\\n1***2***3";
        int result = calculatorService.calculate(input);
        assertThat(result).isEqualTo(6);
    }

    @Test
    void 큰_숫자_입력_테스트() {
        String input = "1000,2000:3000";
        int result = calculatorService.calculate(input);
        assertThat(result).isEqualTo(6000);
    }
}
