package solvesource.com.br.ssprodutos.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MoedaUtil {


    public static final String PORTUGUES = "PT";
    public static final String BRASIL = "br";
    public static final String FORMATO_PADRAO = "R$";
    public static final String FORMATO_DESEJADO = "R$ ";

    public static String formataParaBr(BigDecimal valor) {
        NumberFormat formato_br = DecimalFormat.getCurrencyInstance(new Locale(PORTUGUES, BRASIL));
        return formato_br.format(valor).replace(FORMATO_PADRAO, FORMATO_DESEJADO);
    }


}
