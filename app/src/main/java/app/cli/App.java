/*
 * This file is part of the Valkyrja Framework package.
 *
 * (c) Melech Mizrachi <melechmizrachi@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package app.cli;

import io.valkyrja.application.entry.Cli;

public class App extends Cli {

    static void main(String[] args) {
        run(new Config(), args);
    }
}
