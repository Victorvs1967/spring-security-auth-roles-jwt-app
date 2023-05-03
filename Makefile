build:
	docker build -t victorvsm/backend_api:latest .
	docker push victorvsm/backend_api:latest

start_rep:
	docker-compose -f docker-compose-rep.yml up -d

stop_rep:
	docker-compose -f docker-compose-rep.yml down

start:
	docker-compose up -d

stop:
	docker-compose down