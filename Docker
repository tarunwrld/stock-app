FROM openjdk:17
WORKDIR /
ADD stockapp-0.0.1-SNAPSHOT.jar /stockapp-0.0.1-SNAPSHOT.jar
EXPOSE 7860
COPY entrypoint.sh /entrypoint.sh
# Make the script executable
RUN chmod +x /entrypoint.sh
# Set the entrypoint
ENTRYPOINT ["/entrypoint.sh"]d
