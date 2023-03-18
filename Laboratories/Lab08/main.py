import numpy as np
import random

map_size = (4, 12)
map = np.zeros(map_size)
map[3, 1:11] = 1  # pit
map[3, 11] = 2  # goal
map[3, 0] = 3  # agent
actions = [0, 1, 2, 3]  # up, down, left, right
q_table = np.zeros((map_size[0], map_size[1], len(actions)))
alpha = 0.1
gamma = 0.9
epsilon = 1
episodes = 2000
max_steps_per_episode = 100
reward_per_step = -1
reward_pit = -500
reward_goal = 100
reward_outside = -30


def main(epsilon2):
    for episode in range(episodes):

        # Reset everything
        map = np.zeros(map_size)
        map[3, 1:11] = 1
        map[3, 11] = 2
        map[3, 0] = 3
        agent_position = [3, 0]
        episode_ended = False
        step = 0
        total_reward = 0

        for step in range(max_steps_per_episode):
            exploration_rate = random.uniform(0, 1)
            if exploration_rate > epsilon2:
                action = np.argmax(q_table[agent_position[0], agent_position[1]])
            else:
                action = random.choice(actions)

            if action == 0:  # up
                agent_position[0] -= 1
            elif action == 1:  # down
                agent_position[0] += 1
            elif action == 2:  # left
                agent_position[1] -= 1
            elif action == 3:  # right
                agent_position[1] += 1

            # outside the map -> stay in the same position
            if agent_position[0] < 0 or agent_position[0] >= map_size[0] or agent_position[1] < 0 or agent_position[
                1] >= map_size[1]:
                agent_position[0] = agent_position[0] + 1 if action == 0 else agent_position[0]
                agent_position[0] = agent_position[0] - 1 if action == 1 else agent_position[0]
                agent_position[1] = agent_position[1] + 1 if action == 2 else agent_position[1]
                agent_position[1] = agent_position[1] - 1 if action == 3 else agent_position[1]
                reward = reward_outside
            else:
                # fell into the pit
                if map[agent_position[0], agent_position[1]] == 1:
                    reward = reward_pit
                    episode_ended = True
                    print("CAZUT")
                # reached the goal
                elif map[agent_position[0], agent_position[1]] == 2:
                    reward = reward_goal
                    episode_ended = True
                else:
                    reward = reward_per_step

            q_table[agent_position[0], agent_position[1], action] = q_table[agent_position[0], agent_position[
                1], action] + alpha * (reward + gamma * np.max(
                q_table[agent_position[0], agent_position[1]]) - q_table[agent_position[0], agent_position[1], action])

            total_reward += reward
            map[agent_position[0], agent_position[1]] = 3
            if episode_ended:
                break

        epsilon2 = 1 - episode / episodes
        print("Episode: " + str(episode))
        print("Total reward: " + str(total_reward))
        print("Step: " + str(step))
        print(map)


if __name__ == '__main__':
    main(epsilon)
