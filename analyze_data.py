import pprint, os.path
import numpy as np
import matplotlib.pyplot as plt

pp = pprint.PrettyPrinter(depth=6)

ks = range(1, 10)
vertices = range(25, 250, 25)
densities = [0.7, 0.7999999999999999, 0.8999999999999999, 0.9999999999999999]

filepath = 'datapoints/'
filetypes = ['TZ', 'Greedy']
filemetas = ['_density', '_vertices', '_k']
#ilename = 'datapoints/TZ_density1.0_vertices200_k1.csv'

#measurements = ['density', 'weight', 'highest degree', 'runtime']
measurements = ['weight','density','highest degree','runtime','stretch','jumpstretch']

def generate_filenames(meta):
    density = meta['density']
    vertices = meta['vertices']
    k = meta['k']

    #for t in filetypes:
    names = {}
    for t in filetypes:
        names[t] = filepath + t + '_density' + str(density) + '_vertices' + str(vertices) + '_k' + str(k) + '.csv'

    return names

def load_data_from_files(filenames):


    data = {}

    for t in filetypes:
        n = filenames[t]
        data[t] = load_data_from_file(n)

    return data


def load_data_from_file(filename):

    datafile = open(filename, 'r')

    if os.path.getsize(filename) == 0:
        raise Exception("File empty")

    headers = datafile.readline()
    # Remove trailing newline, and split string into a list over commas
    headers = headers[0:len(headers)-1].split(',')

    lines = datafile.readlines()
    # Remove trailing newline, and split string into a list over commas
    lines = [ line[0:len(line)-1].split(",") for line in lines]

    mean = average_string_readings(lines)

    data = {}

    for i in range(0, len(headers)):
        data[headers[i]] = mean[i]

    return data


def average_string_readings(data):

    # Convert list entries to floats
    floatlines = []
    for line in data:
        floatlines.append( [float(l) for l in line] )

    mean = np.mean(floatlines, axis=0)

    return mean


dicts = []

'''
    dicts = [
        {'k': z,
            'weights': [{'G': x, 'TZ': y}],
            'degree': [{'G': x, 'TZ': y}]
        },
        {'vertices': z,
            'weights': [{'G': x, 'TZ': y}],
            'degree': [{'G': x, 'TZ': y}]
        }
    ]
'''

def initialize_results_dicts():
    for k in ks:
        new_dict = {}
        new_dict['k'] = k

        dicts.append(new_dict)

    for v in vertices:
        new_dict = {}
        new_dict['vertices'] = v

        dicts.append(new_dict)

    for d in densities:
        new_dict = {}
        new_dict['density'] = d

        dicts.append(new_dict)

    for d in dicts:
        for m in measurements:
            entry = {}
            for t in filetypes:
                entry[t] = np.NaN
            d[m] = entry



def select_dicts_by_meta(meta):

    density = meta['density']
    vertices = meta['vertices']
    k = meta['k']

    return_dicts = []

    for d in dicts:
        if 'k' in d and d['k'] == k:
            return_dicts.append(d)

        if 'vertices' in d and d['vertices'] == vertices:
            return_dicts.append(d)

        if 'density' in d and d['density'] == density:
            return_dicts.append(d)

    return return_dicts

def select_dicts_by_metaword(word):

    return_dicts = []

    for d in dicts:
        if word in d:
            return_dicts.append(d)

    return return_dicts

def insert_into_dicts(meta, data):
    idicts = select_dicts_by_meta(meta)

    for d in idicts:
        for m in measurements:
            for t in filetypes:
                d[m][t] = data[t][m]

def plot_points():
    params = ['k', 'vertices']

    for p in params:
        data = select_dicts_by_metaword(p)
        for m in measurements:
            greedy = []
            tz = []

            for d in data:
                greedy.append(d[m]['Greedy'])
                tz.append(d[m]['TZ'])

            plt.clf()

            plt.title(m)
            plt.xlabel(p)
            plt.ylabel(m)
            
            if p == 'k':
                x = ks
            elif p == 'vertices':
                x = vertices
            else:
                x = densities

            g = plt.plot(x, greedy, label='Greedy')
            t = plt.plot(x, tz, label='TZ')

            plt.legend()


            plt.savefig("plots/" + p + "_"+ m)

if __name__ == '__main__':

    initialize_results_dicts()

    for k in ks:
        for d in densities:
            for v in vertices:
                meta = {'density': d, 'vertices': v, 'k': k}

                filenames = generate_filenames(meta)
                try:
                    data = load_data_from_files(filenames)
                    insert_into_dicts(meta, data)
                except Exception as e:
                    break
    '''
    meta1 = {'density': 1.0, 'vertices': 200, 'k': 1}
    data1 = load_data_from_files(meta1)
    insert_into_dicts(meta1, data1)

    meta2 = {'density': 1.0, 'vertices': 175, 'k': 1}
    dicts1 = select_dicts_by_meta(meta1)
    data2 = load_data_from_files(meta2)
    insert_into_dicts(meta2, data2)
    '''

    ds = select_dicts_by_metaword('vertices')
    plot_points()
    #pp.pprint(dicts)
    #pp.pprint(ds[1]['density']['Greedy'])
