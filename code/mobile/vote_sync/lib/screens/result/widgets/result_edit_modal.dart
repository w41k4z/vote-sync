import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/models/candidate.dart';

class ResultEditModal extends StatefulWidget {
  final int nullVotes;
  final int blankVotes;
  final List<Candidate> candidates;

  const ResultEditModal({
    super.key,
    required this.nullVotes,
    required this.blankVotes,
    required this.candidates,
  });

  @override
  State<ResultEditModal> createState() => _ResultEditModalState();
}

class _ResultEditModalState extends State<ResultEditModal> {
  final _formKey = GlobalKey<FormState>();
  late TextEditingController _nullVotesController;
  late TextEditingController _blankVotesController;
  late List<TextEditingController> _candidatesControllers;

  @override
  void initState() {
    super.initState();
    _nullVotesController =
        TextEditingController(text: widget.nullVotes.toString());
    _blankVotesController =
        TextEditingController(text: widget.blankVotes.toString());
    _candidatesControllers = List.generate(
      widget.candidates.length,
      (index) => TextEditingController(
        text: widget.candidates[index].votes.toString(),
      ),
    );
  }

  @override
  void dispose() {
    _nullVotesController.dispose();
    _blankVotesController.dispose();
    for (int i = 0; i < _candidatesControllers.length; i++) {
      _candidatesControllers[i].dispose();
    }
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(20.0),
      child: Form(
        key: _formKey,
        child: SingleChildScrollView(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              const Text(
                'Modifier',
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 20),
              _buildNumberInputField(_nullVotesController, 'Nuls'),
              const SizedBox(height: 16),
              _buildNumberInputField(_blankVotesController, 'Blancs'),
              const SizedBox(height: 15),
              //
              const Divider(),
              //
              const SizedBox(height: 15),
              for (int i = 0; i < 4; i++) ...[
                _buildNumberInputField(
                    _candidatesControllers[i], 'Candidat N° ${i + 1}'),
                if (i != 3) const SizedBox(height: 16),
              ],
              const SizedBox(height: 24),
              ElevatedButton(
                style: ElevatedButton.styleFrom(
                  padding: const EdgeInsets.symmetric(vertical: 8),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                  backgroundColor: AppColors.primaryGreen,
                ),
                onPressed: () {
                  if (_formKey.currentState!.validate()) {
                    final nulls = int.tryParse(_nullVotesController.text) ?? 0;
                    final blanks =
                        int.tryParse(_blankVotesController.text) ?? 0;
                    Navigator.pop(
                      context,
                      {
                        'nullVotes': nulls,
                        'blankVotes': blanks,
                        'candidates': _candidatesControllers
                            .map((e) => int.tryParse(e.text) ?? 0)
                            .toList()
                      },
                    );
                  }
                },
                child: const Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Text(
                      'Enregistrer',
                      style: TextStyle(color: Colors.white, fontSize: 16),
                      textAlign: TextAlign.center,
                    ),
                    SizedBox(width: 10), // Spacing between icon and text
                    Icon(Icons.save, size: 28, color: Colors.white),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildNumberInputField(
      TextEditingController controller, String label) {
    return TextFormField(
      controller: controller,
      keyboardType: const TextInputType.numberWithOptions(
        decimal: false,
        signed: false,
      ),
      decoration: InputDecoration(
        labelText: label,
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10),
        ),
      ),
      validator: (value) {
        if (value == null || value.isEmpty) {
          return 'Veillez entrer une valeur';
        }
        if (int.tryParse(value) == null) {
          return 'Entrez une valeur numérique valide';
        }
        if (int.parse(value) < 0) {
          return 'Entrez une valeur positive';
        }
        return null;
      },
    );
  }
}
