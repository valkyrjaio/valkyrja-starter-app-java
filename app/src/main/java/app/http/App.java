/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.http;

import io.valkyrja.application.entry.ExchangeHttp;
import java.io.IOException;

public class App extends ExchangeHttp {

    public static void main(String[] args) throws IOException {
        run(new Config());
    }
}
