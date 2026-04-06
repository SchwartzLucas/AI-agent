# AI-agent
Repositório criado para documentar código da aula de Inteligência artificial



## Stage 1 - Agente reativo simples

O agente não mantém memória das posições já visitadas nem uma representação interna do ambiente.

Sua decisão de movimento é baseada apenas na percepção atual, isto é, na posição em que se encontra e na presença de limites do grid nas direções possíveis de deslocamento.

Nesta etapa, o ambiente é um grid vazio, sem obstáculos.

### PEAS do agente:

P - Performance (Desempenho):
- Visitar todos os quatro cantos do grid (obrigatório)
- Não sair dos limites do grid [9][9], [0][9], [9][0], [0][0]
- Não ficar preso em loop infinito
- Minimizar número de passos (desejável)
- Completar tarefa de forma autônoma

E - Environment (Ambiente):
- Grid 10x10
- Ambiente estático
- Sem nenhum obstáculo

A - Actuators (Atuadores):
- moverCima()
- moverBaixo()
- moverEsquerda()
- moverDireita()

S - Sensors (Sensores):
- marcarCantoVisitado(x,y)


## Stage 2 - Agente Reativo Baseado em Modelo

### PEAS do agente:

P - Performance (Desempenho):
- Visitar os quatro cantos do grid
- Minimizar número de passos
- Não sair dos limites
- Evitar loops desnecessários

E - Environment (Ambiente):
- Grid 10x10
- Ambiente estático
- Sem obstáculos móveis

A - Actuators (Atuadores):
- moverCima()
- moverBaixo()
- moverEsquerda()
- moverDireita()

S - Sensors (Sensores):
- lerPosicaoAtual()
- verificarSePodeMover(x, y)
- verificarSeEstaEmCanto(x, y)
- verificarCasasAdjacentes()

## Stage 3 - Agente baseado em objetivo

### PEAS do agente:

P – Performance (Desempenho)
-	Encontrar o menor caminho válido entre a posição inicial e a posição objetivo
-	Alcançar o objetivo de forma correta e completa
-	Evitar colisões com obstáculos
-	Não sair dos limites do grid
-	Não revisitar estados desnecessariamente (evitar loops)
-	Minimizar o número de movimentos realizados
-	Encerrar corretamente caso não exista caminho possível

E – Environment (Ambiente)
-	Grid 10x10
-	Ambiente estático e totalmente observável
-	Presença de obstáculos fixos (imutáveis)
-	Posição inicial e posição objetivo definidas pelo usuário
-	Ambiente discreto (movimentos em células)
-	Ambiente determinístico (mesma ação → mesmo resultado)


A – Actuators (Atuadores)
-	moverCima()
-	moverBaixo()
-	moverEsquerda()
-	moverDireita()

S – Sensors (Sensores)
-	Leitura da posição atual (x, y)
-	Verificação de células válidas dentro do grid
-	Detecção de obstáculos nas posições adjacentes
-	Identificação da posição objetivo
-	Mapeamento das células visitadas (controle interno)
-	Exploração das posições vizinhas durante a busca (BFS)


## Stage 4 - Agente baseado em Utilizade

### PEAS do agente:

P – Performance (Desempenho)
- Custo total do caminho (soma dos custos das células percorridas) - PRIMÁRIA
- Número de células visitadas (menor é melhor como secundária)
- Tempo de execução do algoritmo (eficiência computacional)


E – Environment (Ambiente)
- Grid MxN com custos de entrada em cada célula (terrenos diferentes)
- Ponto de partida fixo: [startX][startY]
- Ponto de destino fixo: [destX][destY]
- Células com custos: {1, 2, 3}
- Variação 1: Completamente observável (todos os custos conhecidos)
- Variação 2: Parcialmente observável (custos revelados na exploração)


A – Actuators (Atuadores)
- Mover para células adjacentes (cima, baixo, esquerda, direita)
- Registrar estado interno (células visitadas, custos conhecidos)
- Calcular e atualizar custo acumulado do caminho atual

S – Sensors (Sensores)
- Variação 1 (Observável):
- Custo de entrada de TODAS as células do grid
- Posição atual do agente
- Posição do destino

Variação 2 (Parcialmente observável):
- Custo da célula atual
- Custo das células adjacentes (percepção local)
- Posição atual do agente
- Posição do destino (conhecida)