/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.cli.command;

import app.cli.controller.abstract_.Controller;
import app.cli.provider.CliRouteProvider;
import io.valkyrja.application.data.contract.CliConfigContract;
import io.valkyrja.cli.interaction.input.contract.InputContract;
import io.valkyrja.cli.interaction.message.Answer;
import io.valkyrja.cli.interaction.message.Message;
import io.valkyrja.cli.interaction.message.NewLine;
import io.valkyrja.cli.interaction.message.Question;
import io.valkyrja.cli.interaction.message.contract.AnswerContract;
import io.valkyrja.cli.interaction.output.contract.OutputContract;
import io.valkyrja.cli.interaction.output.factory.contract.OutputFactoryContract;
import io.valkyrja.cli.routing.attribute.Route;
import io.valkyrja.cli.routing.attribute.route.RouteHandler;

public class TestCommand extends Controller {

    protected static final String YES_ANSWER = "yes";
    protected static final String NO_ANSWER = "no";

    public TestCommand(InputContract input, OutputFactoryContract outputFactory) {
        super(input, outputFactory);
    }

    @Route(name = "test", description = "Test command")
    @RouteHandler(handlerClass = CliRouteProvider.class, handlerMethod = "testCommandHandler")
    public OutputContract run(CliConfigContract config) {
        return outputFactory
                .createOutput()
                .withAddedMessages(
                        new Message(config.namespace() + " v" + config.version()),
                        new NewLine(),
                        new Question(
                                "This is a question, right?",
                                this::answered,
                                new Answer(NO_ANSWER).withAllowedResponses(YES_ANSWER, NO_ANSWER)));
    }

    public OutputContract answered(OutputContract output, AnswerContract answer) {
        if (answer.getUserResponse().equals(YES_ANSWER)) {
            return output.withAddedMessages(
                            new Message("You answered yes!!!"), new NewLine(), new NewLine())
                    .writeMessages();
        }

        return output.withAddedMessages(new NewLine());
    }
}
