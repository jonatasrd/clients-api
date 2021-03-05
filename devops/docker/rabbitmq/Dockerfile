FROM rabbitmq:3-management

COPY plugins/*.ez $RABBITMQ_HOME/plugins/
RUN rabbitmq-plugins enable --offline rabbitmq_delayed_message_exchange