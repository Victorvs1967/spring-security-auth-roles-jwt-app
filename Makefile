build:
	docker build -t victorvsm/backend_api:1.0 .
	docker push victorvsm/backend_api:1.0

start_rep:
	docker-compose -f docker-compose-rep.yml up -d --build

stop_rep:
	docker-compose -f docker-compose-rep.yml down

start:
	docker-compose up -d --build

stop:
	docker-compose down